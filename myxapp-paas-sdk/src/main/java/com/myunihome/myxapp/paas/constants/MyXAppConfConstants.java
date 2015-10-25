package com.myunihome.myxapp.paas.constants;

public final class MyXAppConfConstants {

    private MyXAppConfConstants() {

    }

    /**
     * PaaS配置中心配置文件
     */
    public static final String PAAS_CONFIG_FILE = "myxapp.properties";

    /**
     * 缓存配置信息<br/>
     * <p/>
     * 示例数据：
     * {
     *   "default":
     *   {
     * 	  	  "jedisPoolConfig":{"maxTotal":"500","maxIdle":"5","maxWaitMillis":"10000","testOnBorrow":"true"}, 
     * 		  "jedisHostAndPorts":"192.168.0.11:6379,192.168.0.12:6379,192.168.0.13:6379", 
     * 		  "password":"123456"
     *   },
     *   "unisession":
     *   {
     * 	  	  "jedisPoolConfig":{"maxTotal":"500","maxIdle":"5","maxWaitMillis":"10000","testOnBorrow":"true"}, 
     * 		  "jedisHostAndPorts":"192.168.0.21:6379,192.168.0.22:6379,192.168.0.23:6379", 
     * 		  "password":"123456"
     *   },
     *   "docstore":
     *   {
     * 	  	  "jedisPoolConfig":{"maxTotal":"500","maxIdle":"5","maxWaitMillis":"10000","testOnBorrow":"true"}, 
     * 		  "jedisHostAndPorts":"192.168.0.31:6379,192.168.0.32:6379,192.168.0.33:6379", 
     * 		  "password":"123456"
     *   }
     * }
     */
    public static final String PAAS_CACHE_CONFIG_PATH = "/cache/config";
    
    /**
     * 文档存储配置信息<br/>
     * <p/>
     * 
     * 示例数据：
     * {
     *   "default":
     *   {
     * 		  "mongoDBHostAndPorts":"192.168.0.11:27017,192.168.0.12:27017,192.168.0.13:27017",
     * 		  "mongoDBDataBaseName":"myappdb01",
     * 		  "mongoDBUserName":"myappdbusr01",
     * 	  	  "mongoDBPassword":"password",
     * 		  "redisHostAndPorts":"192.168.0.21:6379,192.168.0.22:6379,192.168.0.23:6379",
     * 		  "mongoDBGridFSBucket":"mygridfs01",
     * 		  "mongoDBGridFSFileLimitSize":"1024",
     * 		  "mongoDBGridFSMaxSize":"1"
     *   },
     *   "doorweb":
     *   {
     * 		  "mongoDBHostAndPorts":"192.168.0.11:27017,192.168.0.12:27017,192.168.0.13:27017",
     * 		  "mongoDBDataBaseName":"myappdb01",
     * 		  "mongoDBUserName":"myappdbusr01",
     * 	  	  "mongoDBPassword":"password",
     * 		  "redisHostAndPorts":"192.168.0.21:6379,192.168.0.22:6379,192.168.0.23:6379",
     * 		  "mongoDBGridFSBucket":"mygridfs01",
     * 		  "mongoDBGridFSFileLimitSize":"1024",
     * 		  "mongoDBGridFSMaxSize":"1"
     *   },
     *   "crmweb":
     *   {
     * 		  "mongoDBHostAndPorts":"192.168.0.11:27017,192.168.0.12:27017,192.168.0.13:27017",
     * 		  "mongoDBDataBaseName":"myappdb01",
     * 		  "mongoDBUserName":"myappdbusr01",
     * 	  	  "mongoDBPassword":"password",
     * 		  "redisHostAndPorts":"192.168.0.21:6379,192.168.0.22:6379,192.168.0.23:6379",
     * 		  "mongoDBGridFSBucket":"mygridfs01",
     * 		  "mongoDBGridFSFileLimitSize":"1024",
     * 		  "mongoDBGridFSMaxSize":"1"
     *   }
     *    
     * }
     */
    public static final String PAAS_DOCSTORE_CONFIG_PATH = "/docstore/config";
    
    //目前无需配置，直接用缓存中心的配置
    public static final String PAAS_UNISESSION_CONFIG_PATH = "/unisession/config";
    
    /**
     * 消息生产者配置
     * 示例数据：
     * {
     * 		"metadata.broker.list":"192.168.0.10:9092,192.168.0.10:9093,192.168.0.10:9094",
     * 		"serializer.class":"kafka.serializer.StringEncoder",
     * 		"producer.type":"async",
     * 		"batch.num.messages":"100"
     * } 
     */
    public static final String PAAS_UNIMESSAGE_PRODUCER_CONFIG_PATH="/unimessage/producer/config";
    
    /**
     * 消息消费者配置
     * 示例数据：
     * {
     * 		"group.id":"myxapp-kafka-test-group",
     * 		"zookeeper.connect":"192.168.0.10:2181",
     * 		"zookeeper.connectiontimeout.ms":"1000000"
     * } 
     */
    public static final String PAAS_UNIMESSAGE_CONSUMER_CONFIG_PATH="/unimessage/consumer/config";
    
    
    
    public static final String PAAS_SSO_CONFIG_PATH = "/sso/config";
    
    /**
     * HikariCP数据源配置信息<br/>
     * <p/>
     * 
     * 示例数据：
     * {
     * 		"dataSourceName":"HikariCP|Druid|DBCP|C3p0",
     * 		"driverClassName":"com.mysql.jdbc.Driver",
     * 		"jdbcUrl":"jdbc:mysql://10.1.228.222:39306/devrunnerospdb1?useUnicode=true&characterEncoding=UTF-8",
     * 		"username":"devrunnerospusr1",
     * 		"password":"devrunnerospusr1",
     * 		"autoCommit":"true",
     * 		"connectionTimeout":"30000",
     * 		"idleTimeout":"600000",
     * 		"maxLifetime":"1800000",
     * 		"minimumIdle":"1",
     * 		"maximumPoolSize":"100",
     * 
     * 		"sequenceTable":"sys_sequences"
     * } 
     */
    public static final String PAAS_DATASOURCE_HIKARICP_CONFIG_PATH = "/datasource/hikaricp/config";
    
    /**
     * Druid数据源配置信息<br/>
     * <p/>
     * 
     * 示例数据：
     * {
     * 		"dataSourceName":"Druid",
     * 		"driverClassName":"com.mysql.jdbc.Driver",
     * 		"url":"jdbc:mysql://10.1.228.222:39306/devrunnerospdb1?useUnicode=true&characterEncoding=UTF-8",
     * 		"username":"devrunnerospusr1",
     * 		"password":"devrunnerospusr1",
     *      "filters":"stat",
     * 		"initialSize":"1",
     * 		"maxActive":"100",
     * 		"minIdle":"0",
     * 		"maxWait":"1000",
     * 
     * 		"testWhileIdle":"true",
     * 		"testOnBorrow":"false",
     * 		"testOnReturn":"false",
     * 		"validationQuery":"select 1",
     * 		"timeBetweenEvictionRunsMillis":"3000",
     * 		"minEvictableIdleTimeMillis":"3600000",
     * 
     * 		"poolPreparedStatements":"true",
     * 		"maxPoolPreparedStatementPerConnectionSize":"50",
     * 
     * 		"sequenceTable":"sys_sequences"
     * } 
     */
    public static final String PAAS_DATASOURCE_DRUID_CONFIG_PATH = "/datasource/druid/config";
    
    /**
     * DBCP数据源配置信息<br/>
     * <p/>
     * 
     * 示例数据：
     * {
     * 		"dataSourceName":"DBCP",
     * 		"driverClassName":"com.mysql.jdbc.Driver",
     * 		"url":"jdbc:mysql://10.1.228.222:39306/devrunnerospdb1?useUnicode=true&characterEncoding=UTF-8",
     * 		"username":"devrunnerospusr1",
     * 		"password":"devrunnerospusr1",
     * 		"initialSize":"1",
     * 		"maxActive":"100",
     * 		"maxIdle":"1",
     * 		"minIdle":"0",
     * 		"maxWait":"1000",
     * 		"defaultAutoCommit":"true",
     * 		"connectionProperties":"useUnicode=true;characterEncoding=UTF-8",
     * 		"testWhileIdle":"true",
     * 		"testOnBorrow":"false",
     * 		"testOnReturn":"false",
     * 		"validationQuery":"select 1",
     * 		"timeBetweenEvictionRunsMillis":"3000",
     * 		"numTestsPerEvictionRun":"100",
     * 		"removeAbandoned":"true",
     * 		"removeAbandonedTimeout":"1",
     * 		"logAbandoned":"true",
     * 
     * 		"sequenceTable":"sys_sequences"
     * } 
     */
    public static final String PAAS_DATASOURCE_DBCP_CONFIG_PATH = "/datasource/dbcp/config";
    
    /**
     * C3P0数据源配置信息<br/>
     * <p/>
     * 
     * 示例数据：
     * {
     * 		"dataSourceName":"C3P0",
     * 		"driverClass":"com.mysql.jdbc.Driver",
     * 		"jdbcUrl":"jdbc:mysql://10.1.228.222:39306/devrunnerospdb1?useUnicode=true&characterEncoding=UTF-8",
     * 		"user":"devrunnerospusr1",
     * 		"password":"devrunnerospusr1",
     * 		"minPoolSize":"1",
     * 		"maxPoolSize":"100",
     * 		"initialPoolSize":"1",
     * 		"maxIdleTime":"1",
     * 		"acquireIncrement":"1",
     * 		"acquireRetryAttempts":"30",
     * 		"acquireRetryDelay":"1000",
     * 		"testConnectionOnCheckin":"true",
     * 		"automaticTestTable":"c3p0TestTable",
     * 		"idleConnectionTestPeriod":"1",
     * 		"checkoutTimeout":"3000",
     * 
     * 		"sequenceTable":"sys_sequences"
     * } 
     */
    public static final String PAAS_DATASOURCE_C3P0_CONFIG_PATH = "/datasource/c3p0/config";
    
    public static final String PAAS_TXS_CONFIG_PATH = "/txs/config";
    public static final String PAAS_ATXS_CONFIG_PATH = "/atxs/config";
    public static final String PAAS_MQ_CONFIG_PATH = "/mq/config";
    
    
    
    public static final String PAAS_CACHENS_MCS_MAPPED_PATH = "/com/gucl/myapp/paas-cachens-mcs-mapped";

    /**
     * ServiceID和其对应密码，用户名和ServiceID对应关系，每个ServiceID都有一条配置项<br/>
     * <p/>
     * 例如ServiceIdA的配置如下： /com/gucl/myapp/paas-service-pwd-mapped/mcs {"MCS001":"password"}
     *
     * /com/gucl/myapp/paas-service-pwd-mapped/txs {"TXS001":"password"}
     * /com/gucl/myapp/paas-service-pwd-mapped/dss {"DSS001":"password"}
     */
    public static final String PAAS_SERVICE_PWD_MAPPED_PATH = "/com/gucl/myapp/paas-service-pwd-mapped/";

    /**
     * 当前系统需要使用的TXS服务ID是什么 /com/gucl/myapp/paas-txs TXS001
     */
    public static final String PAAS_TXS_PATH = "/com/gucl/myapp/paas-txs";

    /**
     * 当前系统需要使用的DBS服务ID是什么 /com/gucl/myapp/paas-dbs DBS001
     */
    public static final String PAAS_DBS_PATH = "/com/gucl/myapp/paas-dbs";

    /**
     * 引用该Util的中心产品的ATS的SignId，可以有多个,以逗号隔开。格式如下：
     * <p/>
     * /com/gucl/myapp/paas-ats-signatureId signatureId-bd5ff9b1-6f4d-4bbe-899e-840fc57b4cf0
     */
    public static final String PAAS_ATS_SIGNATUREID_PATH = "/com/gucl/myapp/paas-ats-signatureId";
    
    /**
     * 配置ATS服务对应的签名ID {key:value} 其中key="com.xx.xx.IXXSV#method1" value="signatureId"
     */
    public static final String PAAS_ATS_SERVICE_SIGNATUREID_MAPPED_PATH  = "/com/gucl/myapp/paas-atsservice-signatureId-mapped";

}
