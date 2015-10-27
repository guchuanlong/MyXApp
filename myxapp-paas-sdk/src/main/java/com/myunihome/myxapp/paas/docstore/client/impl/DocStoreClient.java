package com.myunihome.myxapp.paas.docstore.client.impl;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSInputFile;
import com.myunihome.myxapp.paas.cache.client.ICacheClient;
import com.myunihome.myxapp.paas.docstore.client.IDocStoreClient;
import com.myunihome.myxapp.paas.docstore.exception.DocStoreRuntimeException;

import redis.clients.jedis.JedisCluster;

public class DocStoreClient implements IDocStoreClient{
  private static final Logger log = LogManager.getLogger(DocStoreClient.class);
  private static final String FILE_NAME = "filename";
  private static final String REMARK = "remark";
  private MongoClient mongoClient;
  private DB db;
  private static String gridFSBucket;
  private static double gridFSMaxSize;
  private static double fileLimitSize;
//  private static JedisCluster jedisCluster;//redis集群客户端
  private static String redisKey;
  private static ICacheClient cacheClient;//缓存客户端

  
  @SuppressWarnings("deprecation")
  public DocStoreClient(String mongoHostAndPorts, String mongoDataBaseName, String mongoUserName, String mongoPassword, String mongoGridFSBucket,double mongoGridFSMaxSize,double mongoGridFSFileLimitSize,ICacheClient redisCacheClient)
  {
    MongoCredential credential = MongoCredential.createCredential(mongoUserName, mongoDataBaseName, mongoPassword.toCharArray());

    this.mongoClient = new MongoClient(DocStoreHelper.Str2ServerAddressList(mongoHostAndPorts), Arrays.asList(new MongoCredential[] { credential }));

    this.db = this.mongoClient.getDB(mongoDataBaseName);
    gridFSBucket = mongoGridFSBucket;
    fileLimitSize = mongoGridFSFileLimitSize;
    gridFSMaxSize = mongoGridFSMaxSize;
    redisKey = mongoDataBaseName + mongoGridFSBucket;
    cacheClient=redisCacheClient;
  }
  
  
/*  @SuppressWarnings("deprecation")
public DocStoreClient(String mongoHostAndPorts, String mongoDataBaseName, String mongoUserName, String mongoPassword, String redisHostAndPorts, String mongoGridFSBucket,double mongoGridFSMaxSize,double mongoGridFSFileLimitSize)
  {
    MongoCredential credential = MongoCredential.createCredential(mongoUserName, mongoDataBaseName, mongoPassword.toCharArray());

    this.mongoClient = new MongoClient(DocStoreHelper.Str2ServerAddressList(mongoHostAndPorts), Arrays.asList(new MongoCredential[] { credential }));

    this.db = this.mongoClient.getDB(mongoDataBaseName);
    gridFSBucket = mongoGridFSBucket;
    fileLimitSize = mongoGridFSFileLimitSize;
    gridFSMaxSize = mongoGridFSMaxSize;
//    jedisCluster = DocStoreHelper.getRedis(redisHostAndPorts);
    redisKey = mongoDataBaseName + mongoGridFSBucket;
  }*/
  
  public String save(File file, String remark)
  {
    long usedSize = cacheClient.incrBy(redisKey, 0L).longValue();
    if (DocStoreHelper.okSize(DocStoreHelper.M2byte(fileLimitSize), DocStoreHelper.getFileSize(file)) < 0L)
    {
      log.error("file too large");
      throw new DocStoreRuntimeException(new Exception("file too large"));
    }
    if (DocStoreHelper.okSize(DocStoreHelper.okSize(DocStoreHelper.M2byte(gridFSMaxSize), usedSize), DocStoreHelper.getFileSize(file)) < 0L)
    {
      log.error("left size not enough");
      throw new DocStoreRuntimeException(new Exception("left size not enough"));
    }
    String fileType = DocStoreHelper.getFileType(file.getName());
    GridFS fs = new GridFS(this.db, gridFSBucket);
    try
    {
      GridFSInputFile dbFile = fs.createFile(file);
      DBObject dbo = new BasicDBObject();
      dbo.put(REMARK, remark);
      dbFile.setMetaData(dbo);
      dbFile.setContentType(fileType);
      dbFile.save();
      cacheClient.incrBy(redisKey, Integer.parseInt(DocStoreHelper.getFileSize(file) + ""));

      return dbFile.getId().toString();
    } catch (Exception e) {
      log.error(e.getMessage(),e);
      throw new DocStoreRuntimeException(e);
    }
  }

  public String save(byte[] bytes, String remark)
  {
    if (bytes == null) {
      log.error("bytes illegal");
      throw new DocStoreRuntimeException(new Exception("bytes illegal"));
    }
    long usedSize = cacheClient.incrBy(redisKey, 0L).longValue();
    if (DocStoreHelper.okSize(DocStoreHelper.M2byte(fileLimitSize), DocStoreHelper.getFileSize(bytes)) < 0L)
    {
      log.error("file too large");
      throw new DocStoreRuntimeException(new Exception("file too large"));
    }
    if (DocStoreHelper.okSize(DocStoreHelper.okSize(DocStoreHelper.M2byte(gridFSMaxSize), usedSize), DocStoreHelper.getFileSize(bytes)) < 0L)
    {
      log.error("left size not enough");
      throw new DocStoreRuntimeException(new Exception("left size not enough"));
    }
    GridFS fs = new GridFS(this.db, gridFSBucket);
    try
    {
      GridFSInputFile dbFile = fs.createFile(bytes);
      DBObject dbo = new BasicDBObject();
      dbo.put(REMARK, remark);
      dbFile.setMetaData(dbo);
      dbFile.save();
      cacheClient.incrBy(redisKey, Integer.parseInt(DocStoreHelper.getFileSize(bytes) + ""));

      return dbFile.getId().toString();
    } catch (Exception e) {
      log.error(e.toString());
      throw new DocStoreRuntimeException(e);
    }
  }

  public byte[] read(String id)
  {
    if ((id == null) || ("".equals(id))) {
      log.error("id illegal");
      throw new DocStoreRuntimeException(new Exception("id illegal"));
    }
    GridFS fs = new GridFS(this.db, gridFSBucket);
    GridFSDBFile dbFile = fs.findOne(new ObjectId(id));
    if (dbFile == null)
      return null;
    try
    {
      return DocStoreHelper.toByteArray(dbFile.getInputStream());
    } catch (Exception e) {
      log.error(e.toString());
      throw new DocStoreRuntimeException(e);
    }
  }

  public boolean delete(String id)
  {
    if ((id == null) || ("".equals(id))) {
      log.error("id illegal");
      throw new DocStoreRuntimeException(new Exception("id or bytes illegal"));
    }
    GridFS fs = new GridFS(this.db, gridFSBucket);
    GridFSDBFile dbFile = fs.findOne(new ObjectId(id));
    if (dbFile == null) {
      return false;
    }
    fs.remove(dbFile);
    cacheClient.decrBy(redisKey, Integer.parseInt(dbFile.getLength() + ""));
    return true;
  }

  public void update(String id, byte[] bytes)
  {
    long usedSize = cacheClient.incrBy(redisKey, 0L).longValue();
    if ((bytes == null) || (id == null) || ("".equals(id))) {
      log.error("id or bytes illegal");
      throw new DocStoreRuntimeException(new Exception("id or bytes illegal"));
    }
    if (DocStoreHelper.okSize(DocStoreHelper.M2byte(fileLimitSize), DocStoreHelper.getFileSize(bytes)) < 0L)
    {
      log.error("file too large");
      throw new DocStoreRuntimeException(new Exception("file too large"));
    }
    GridFS fs = new GridFS(this.db, gridFSBucket);
    GridFSDBFile dbFile = fs.findOne(new ObjectId(id));
    if (dbFile == null) {
      log.error("file missing");
      throw new DocStoreRuntimeException(new Exception("file missing"));
    }
    String fileName = dbFile.getFilename();
    String fileType = dbFile.getContentType();
    usedSize = cacheClient.decrBy(redisKey, Integer.parseInt(dbFile.getLength() + "")).longValue();

    if (DocStoreHelper.okSize(DocStoreHelper.okSize(DocStoreHelper.M2byte(gridFSMaxSize), usedSize), DocStoreHelper.getFileSize(bytes)) < 0L)
    {
      log.error("left size not enough");
      throw new DocStoreRuntimeException(new Exception("left size not enough"));
    }
    fs.remove(dbFile);
    GridFSInputFile file = fs.createFile(bytes);
    file.setId(new ObjectId(id));
    file.setContentType(fileType);
    file.setFilename(fileName);
    file.put(FILE_NAME, fileName);
    file.save();
    cacheClient.incrBy(redisKey, Integer.parseInt(DocStoreHelper.getFileSize(bytes) + ""));
  }

  public Date getLastUpdateTime(String id)
  {
    if (id == null) {
      log.error("id is null");
      throw new DocStoreRuntimeException(new Exception("id illegal"));
    }
    GridFS fs = new GridFS(this.db, gridFSBucket);
    GridFSDBFile dbFile = fs.findOne(new ObjectId(id));
    if (dbFile == null) {
      log.error("file missing");
      throw new DocStoreRuntimeException(new Exception("file missing"));
    }
    return dbFile.getUploadDate();
  }

  public void update(String id, File file)
  {
    long usedSize = cacheClient.incrBy(redisKey, 0L).longValue();
    if ((file == null) || (id == null) || ("".equals(id))) {
      log.error("id or file illegal");
      throw new DocStoreRuntimeException(new Exception("id or file illegal"));
    }
    if (DocStoreHelper.okSize(DocStoreHelper.M2byte(fileLimitSize), DocStoreHelper.getFileSize(file)) < 0L)
    {
      log.error("file too large");//单文件大小超出限制
      throw new DocStoreRuntimeException(new Exception("file too large"));
    }
    GridFS fs = new GridFS(this.db, gridFSBucket);
    GridFSDBFile dbFile = fs.findOne(new ObjectId(id));
    if (dbFile == null) {
      log.error("file missing");
      throw new DocStoreRuntimeException(new Exception("file missing"));
    }
    String fileName = dbFile.getFilename();
    String fileType = dbFile.getContentType();
    if (DocStoreHelper.okSize(DocStoreHelper.okSize(DocStoreHelper.M2byte(gridFSMaxSize), usedSize - Integer.parseInt(dbFile.getLength() + "")), DocStoreHelper.getFileSize(file)) < 0L)
    {
      log.error("left size not enough");//剩余空间不够
      throw new DocStoreRuntimeException(new Exception("left size not enough"));
    }
    usedSize = cacheClient.decrBy(redisKey, Integer.parseInt(dbFile.getLength() + "")).longValue();

    fs.remove(dbFile);
    GridFSInputFile fsfile = null;
    try {
      fsfile = fs.createFile(file);
    } catch (IOException e) {
      log.error(e.getMessage());
      throw new DocStoreRuntimeException(new Exception(e));
    }
    fsfile.setId(new ObjectId(id));
    fsfile.setContentType(fileType);
    fsfile.setFilename(fileName);
    fsfile.put(FILE_NAME, fileName);
    fsfile.save();
    cacheClient.incrBy(redisKey, Integer.parseInt(DocStoreHelper.getFileSize(file) + ""));
  }

}