package com.myunihome.myxapp.paas.model;

import java.io.Serializable;

public class DocStoreConfigInfo implements Serializable{
	private static final long serialVersionUID = -3413650276342821771L;
	private String mongoDBHostAndPorts;//MonogDB集群示例：192.168.0.11:27017,192.168.0.12:27017,192.168.0.13:27017
	private String mongoDBDataBaseName;//数据库名称
    private String mongoDBUserName;//用户名
	private String mongoDBPassword;//密码
	private String redisHostAndPorts;//Redis集群，示例：192.168.0.21:6379,192.168.0.22:6379,192.168.0.23:6379
	private String mongoDBGridFSBucket;//GridFS下的集合名称，默认为fs
	private double mongoDBGridFSFileLimitSize;// GridFS单个文件大小限制 单位M
	private double mongoDBGridFSMaxSize;//GridFS最大存储量 单位M
	public DocStoreConfigInfo() {
		super();
	}
	
	public DocStoreConfigInfo(String mongoDBHostAndPorts, String mongoDBDataBaseName, String mongoDBUserName,
			String mongoDBPassword, String redisHostAndPorts, String mongoDBGridFSBucket,
			double mongoDBGridFSFileLimitSize, double mongoDBGridFSMaxSize) {
		super();
		this.mongoDBHostAndPorts = mongoDBHostAndPorts;
		this.mongoDBDataBaseName = mongoDBDataBaseName;
		this.mongoDBUserName = mongoDBUserName;
		this.mongoDBPassword = mongoDBPassword;
		this.redisHostAndPorts = redisHostAndPorts;
		this.mongoDBGridFSBucket = mongoDBGridFSBucket;
		this.mongoDBGridFSFileLimitSize = mongoDBGridFSFileLimitSize;
		this.mongoDBGridFSMaxSize = mongoDBGridFSMaxSize;
	}

	public String getMongoDBHostAndPorts() {
		return mongoDBHostAndPorts;
	}
	public void setMongoDBHostAndPorts(String mongoDBHostAndPorts) {
		this.mongoDBHostAndPorts = mongoDBHostAndPorts;
	}
	
	public String getMongoDBDataBaseName() {
		return mongoDBDataBaseName;
	}
	public void setMongoDBDataBaseName(String mongoDBDataBaseName) {
		this.mongoDBDataBaseName = mongoDBDataBaseName;
	}
	public String getMongoDBUserName() {
		return mongoDBUserName;
	}
	public void setMongoDBUserName(String mongoDBUserName) {
		this.mongoDBUserName = mongoDBUserName;
	}
	public String getMongoDBPassword() {
		return mongoDBPassword;
	}
	public void setMongoDBPassword(String mongoDBPassword) {
		this.mongoDBPassword = mongoDBPassword;
	}
	public String getRedisHostAndPorts() {
		return redisHostAndPorts;
	}
	public void setRedisHostAndPorts(String redisHostAndPorts) {
		this.redisHostAndPorts = redisHostAndPorts;
	}
	public String getMongoDBGridFSBucket() {
		return mongoDBGridFSBucket;
	}
	public void setMongoDBGridFSBucket(String mongoDBGridFSBucket) {
		this.mongoDBGridFSBucket = mongoDBGridFSBucket;
	}
	public double getMongoDBGridFSFileLimitSize() {
		return mongoDBGridFSFileLimitSize;
	}
	public void setMongoDBGridFSFileLimitSize(double mongoDBGridFSFileLimitSize) {
		this.mongoDBGridFSFileLimitSize = mongoDBGridFSFileLimitSize;
	}
	public double getMongoDBGridFSMaxSize() {
		return mongoDBGridFSMaxSize;
	}
	public void setMongoDBGridFSMaxSize(double mongoDBGridFSMaxSize) {
		this.mongoDBGridFSMaxSize = mongoDBGridFSMaxSize;
	}
	
}
