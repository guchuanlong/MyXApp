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
     * 		  "mongoDBGridFSBucket":"mygridfs01",
     * 		  "mongoDBGridFSFileLimitSize":"1024",
     * 		  "mongoDBGridFSMaxSize":"1",
     * 		  "cacheNameSpace":"default"
     *   },
     *   "doorweb":
     *   {
     * 		  "mongoDBHostAndPorts":"192.168.0.11:27017,192.168.0.12:27017,192.168.0.13:27017",
     * 		  "mongoDBDataBaseName":"myappdb01",
     * 		  "mongoDBUserName":"myappdbusr01",
     * 	  	  "mongoDBPassword":"password",
     * 		  "mongoDBGridFSBucket":"mygridfs01",
     * 		  "mongoDBGridFSFileLimitSize":"1024",
     * 		  "mongoDBGridFSMaxSize":"1",
     * 		  "cacheNameSpace":"default"
     *   },
     *   "crmweb":
     *   {
     * 		  "mongoDBHostAndPorts":"192.168.0.11:27017,192.168.0.12:27017,192.168.0.13:27017",
     * 		  "mongoDBDataBaseName":"myappdb01",
     * 		  "mongoDBUserName":"myappdbusr01",
     * 	  	  "mongoDBPassword":"password",
     * 		  "mongoDBGridFSBucket":"mygridfs01",
     * 		  "mongoDBGridFSFileLimitSize":"1024",
     * 		  "mongoDBGridFSMaxSize":"1",
     *        "cacheNameSpace":"default"
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
     * HikariCP示例数据：
     * {	
     *      "default":
     * 		{
     * 			"driverClassName":"com.mysql.jdbc.Driver",
     * 			"jdbcUrl":"jdbc:mysql://10.1.228.222:39306/devrunnerospdb1?useUnicode=true&characterEncoding=UTF-8",
     * 			"username":"devrunnerospusr1",
     * 			"password":"devrunnerospusr1",
     * 			"autoCommit":"true",
     * 			"connectionTimeout":"30000",
     * 			"idleTimeout":"600000",
     * 			"maxLifetime":"1800000",
     * 			"minimumIdle":"1",
     * 			"maximumPoolSize":"100",
     * 
     * 			"sequenceTable":"sys_sequences"
     * 
     * 		},
     *      "sso-db":
     * 		{
     * 			"driverClassName":"com.mysql.jdbc.Driver",
     * 			"jdbcUrl":"jdbc:mysql://10.1.228.222:39306/devrunnerospdb1?useUnicode=true&characterEncoding=UTF-8",
     * 			"username":"devrunnerospusr1",
     * 			"password":"devrunnerospusr1",
     * 			"autoCommit":"true",
     * 			"connectionTimeout":"30000",
     * 			"idleTimeout":"600000",
     * 			"maxLifetime":"1800000",
     * 			"minimumIdle":"1",
     * 			"maximumPoolSize":"100",
     * 
     * 			"sequenceTable":"sys_sequences"
     * 
     * 		} 
     * }
     */
    public static final String PAAS_DATASOURCE_HIKARICP_CONFIG_PATH = "/datasource/hikaricp/config";
    
    /**
     * Druid数据源配置信息<br/>
     * <p/>
     * 
     * Druid示例数据：
     * {
     * 		"default":
     * 		{
     * 			"driverClassName":"com.mysql.jdbc.Driver",
     * 			"url":"jdbc:mysql://10.1.228.222:39306/devrunnerospdb1?useUnicode=true&characterEncoding=UTF-8",
     * 			"username":"devrunnerospusr1",
     * 			"password":"devrunnerospusr1",
     *      	"filters":"stat",
     * 			"initialSize":"1",
     * 			"maxActive":"100",
     * 			"minIdle":"0",
     * 			"maxWait":"1000",
     * 
     * 			"testWhileIdle":"true",
     * 			"testOnBorrow":"false",
     * 			"testOnReturn":"false",
     * 			"validationQuery":"select 1",
     * 			"timeBetweenEvictionRunsMillis":"3000",
     * 			"minEvictableIdleTimeMillis":"3600000",
     * 
     * 			"poolPreparedStatements":"true",
     * 			"maxPoolPreparedStatementPerConnectionSize":"50",
     * 
     * 			"sequenceTable":"sys_sequences"
     * 		} ,
     *      "sso-db":
     * 		{
     * 			"driverClassName":"com.mysql.jdbc.Driver",
     * 			"url":"jdbc:mysql://10.1.228.222:39306/devrunnerospdb1?useUnicode=true&characterEncoding=UTF-8",
     * 			"username":"devrunnerospusr1",
     * 			"password":"devrunnerospusr1",
     *      	"filters":"stat",
     * 			"initialSize":"1",
     * 			"maxActive":"100",
     * 			"minIdle":"0",
     * 			"maxWait":"1000",
     * 
     * 			"testWhileIdle":"true",
     * 			"testOnBorrow":"false",
     * 			"testOnReturn":"false",
     * 			"validationQuery":"select 1",
     * 			"timeBetweenEvictionRunsMillis":"3000",
     * 			"minEvictableIdleTimeMillis":"3600000",
     * 
     * 			"poolPreparedStatements":"true",
     * 			"maxPoolPreparedStatementPerConnectionSize":"50",
     * 
     * 			"sequenceTable":"sys_sequences"    
     * 		} 
     * }
     */
    public static final String PAAS_DATASOURCE_DRUID_CONFIG_PATH = "/datasource/druid/config";
    
    /**
     * DBCP数据源配置信息<br/>
     * <p/>
     * 
     * DBCP示例数据：
     * {
     * 		"default":
     * 		{
     * 			"driverClassName":"com.mysql.jdbc.Driver",
     * 			"url":"jdbc:mysql://10.1.228.222:39306/devrunnerospdb1?useUnicode=true&characterEncoding=UTF-8",
     * 			"username":"devrunnerospusr1",
     * 			"password":"devrunnerospusr1",
     * 			"initialSize":"1",
     * 			"maxActive":"100",
     * 			"maxIdle":"1",
     * 			"minIdle":"0",
     * 			"maxWait":"1000",
     * 			"defaultAutoCommit":"true",
     * 			"connectionProperties":"useUnicode=true;characterEncoding=UTF-8",
     * 			"testWhileIdle":"true",
     * 			"testOnBorrow":"false",
     * 			"testOnReturn":"false",
     * 			"validationQuery":"select 1",
     * 			"timeBetweenEvictionRunsMillis":"3000",
     * 			"numTestsPerEvictionRun":"100",
     * 			"removeAbandoned":"true",
     * 			"removeAbandonedTimeout":"1",
     * 			"logAbandoned":"true",
     * 
     * 			"sequenceTable":"sys_sequences"
     * 		},
     *      "sso-db":
     * 		{
     * 			"driverClassName":"com.mysql.jdbc.Driver",
     * 			"url":"jdbc:mysql://10.1.228.222:39306/devrunnerospdb1?useUnicode=true&characterEncoding=UTF-8",
     * 			"username":"devrunnerospusr1",
     * 			"password":"devrunnerospusr1",
     * 			"initialSize":"1",
     * 			"maxActive":"100",
     * 			"maxIdle":"1",
     * 			"minIdle":"0",
     * 			"maxWait":"1000",
     * 			"defaultAutoCommit":"true",
     * 			"connectionProperties":"useUnicode=true;characterEncoding=UTF-8",
     * 			"testWhileIdle":"true",
     * 			"testOnBorrow":"false",
     * 			"testOnReturn":"false",
     * 			"validationQuery":"select 1",
     * 			"timeBetweenEvictionRunsMillis":"3000",
     * 			"numTestsPerEvictionRun":"100",
     * 			"removeAbandoned":"true",
     * 			"removeAbandonedTimeout":"1",
     * 			"logAbandoned":"true",
     * 
     * 			"sequenceTable":"sys_sequences"
     * 		} 
     * }
     */
    public static final String PAAS_DATASOURCE_DBCP_CONFIG_PATH = "/datasource/dbcp/config";
    
    /**
     * C3P0数据源配置信息<br/>
     * <p/>
     * 
     * C3P0示例数据：
     * {
     * 		"default":
     * 		{
     * 			"driverClass":"com.mysql.jdbc.Driver",
     * 			"jdbcUrl":"jdbc:mysql://10.1.228.222:39306/devrunnerospdb1?useUnicode=true&characterEncoding=UTF-8",
     * 			"user":"devrunnerospusr1",
     * 			"password":"devrunnerospusr1",
     * 			"minPoolSize":"1",
     * 			"maxPoolSize":"100",
     * 			"initialPoolSize":"1",
     * 			"maxIdleTime":"1",
     * 			"acquireIncrement":"1",
     * 			"acquireRetryAttempts":"30",
     * 			"acquireRetryDelay":"1000",
     * 			"testConnectionOnCheckin":"true",
     * 			"automaticTestTable":"c3p0TestTable",
     * 			"idleConnectionTestPeriod":"1",
     * 			"checkoutTimeout":"3000",
     * 			"sequenceTable":"sys_sequences"
     * 		}, 
     *      "sso-db":
     * 		{
     * 			"driverClass":"com.mysql.jdbc.Driver",
     * 			"jdbcUrl":"jdbc:mysql://10.1.228.222:39306/devrunnerospdb1?useUnicode=true&characterEncoding=UTF-8",
     * 			"user":"devrunnerospusr1",
     * 			"password":"devrunnerospusr1",
     * 			"minPoolSize":"1",
     * 			"maxPoolSize":"100",
     * 			"initialPoolSize":"1",
     * 			"maxIdleTime":"1",
     * 			"acquireIncrement":"1",
     * 			"acquireRetryAttempts":"30",
     * 			"acquireRetryDelay":"1000",
     * 			"testConnectionOnCheckin":"true",
     * 			"automaticTestTable":"c3p0TestTable",
     * 			"idleConnectionTestPeriod":"1",
     * 			"checkoutTimeout":"3000",
     * 			"sequenceTable":"sys_sequences"
     * 		}
     * }
     */
    public static final String PAAS_DATASOURCE_C3P0_CONFIG_PATH = "/datasource/c3p0/config";
    
    /**
     * 搜索引擎配置信息<br/>
     * <p/>
     * 
     * 搜索引擎配置示例数据：
     * {
     * 		"default":
     * 		{
     * 			"hostAndPorts":"127.0.0.1:9300,192.168.0.10:9300"
     * 		}, 
     *      "sso-search":
     * 		{
     * 			"hostAndPorts":"127.0.0.1:9300,192.168.0.10:9300"
     * 		}
     * }
     */
    public static final String PAAS_SEARCH_CONFIG_PATH = "/search/config";
    
    /**Dubbo服务提供者配置数据
     *  {
     *  	"dubbo.provider.retries":"0",
     *  	"dubbo.registry.address": "localhost:2181",
     *  	"dubbo.provider.timeout":"30000"
     *  }
     */
    public static final String DUBBO_PROVIDER_CONF_PATH = "/dubbo/provider";

    /**Dubbo服务消费者配置数据
     * {
     * 		"default.dubbo.registry.address":"localhost:2181",
     * 		"myxapp-common.registry.address":"localhost:2181",
     * 		"myxapp-sys.registry.address": "localhost:2181"
     * }
     */
    public static final String DUBBO_CONSUMER_CONF_PATH = "/dubbo/consumer";
    
    
    
    
    public static final String PAAS_TXS_CONFIG_PATH = "/txs/config";
    public static final String PAAS_ATXS_CONFIG_PATH = "/atxs/config";
    public static final String PAAS_MQ_CONFIG_PATH = "/mq/config";

}
