package com.myunihome.myxapp.paas.model;

import java.io.Serializable;

public class DBCPDataSourceConfig implements Serializable{
	private static final long serialVersionUID = -3664929924801784994L;
	private String dataSourceName="DBCP";//数据源类型HikariCP|Druid|DBCP|C3P0
	/* 数据库驱动名称
	 * ORACLE :oracle.jdbc.driver.OracleDriver
	 * MYSQL :com.mysql.jdbc.Driver
	 * SQLSERVER ：com.microsoft.sqlserver.jdbc.SQLServerDriver
	 * DB2 ： com.ibm.db2.jcc.DB2Driver
	 * */
	private String driverClassName;//数据库驱动名称
    private String url;//数据库连接串
	private String username;//用户名
	private String password;//密码
	
	//JDBC驱动建立连接时附带的连接属性属性的格式必须为这样：[属性名=property;]
	//注意："user" 与 "password" 两个属性会被明确地传递，因此这里不需要包含他们。
	//示例：useUnicode=true;characterEncoding=GBK
	private String connectionProperties;
	
	//默认值是0, 连接池创建连接的初始连接数目
	private int initialSize=1;
	//默认值是8, 连接池中同时可以分派的最大活跃连接数
	private int maxActive=100;
	//默认是8，连接池中最大空闲连接数
	private int maxIdle=1;
	//默认值是无限大，当连接池中连接已经用完了，等待建立一个新连接的最大毫秒数(在抛异常之前)超时等待时间以毫秒为单位 1000毫秒/1000等于1秒 
	private int maxWait=1000;
	
	//指定由连接池所创建的连接的自动提交（auto-commit）状态。
	private boolean defaultAutoCommit=true;
	
	//==数据库断网或重启自动重连配置==
	//定时对线程池中的链接进行validateObject校验，对无效的链接进行关闭后，会调用ensureMinIdle，适当建立链接保证最小的minIdle连接数
	private boolean testWhileIdle=true;
	//在进行borrowObject进行处理时，对拿到的connection进行validateObject校验
	private boolean testOnBorrow=false;
	//在进行returnObject对返回的connection进行validateObject校验，个人觉得对数据库连接池的管理意义不大
	private boolean testOnReturn=false;
	private String validationQuery="select 1";
	//设置的Evict线程的时间，单位ms，大于0才会开启evict检查线程
	private int timeBetweenEvictionRunsMillis=3000;
	//代表每次检查链接的数量，建议设置和maxActive一样大，这样每次可以有效检查所有的链接
	private int numTestsPerEvictionRun=100;
	//==数据库断网或重启自动重连配置==
	
	//==数据库连接回收机制==
	//默认值是false, 是否清理removeAbandonedTimeout秒没有使用的活动连接,清理后并没有放回连接池
	private boolean removeAbandoned=true;
	//超过时间限制，回收没有用(废弃)的连接（默认为 300秒）
	private int removeAbandonedTimeout=1;
	//标记当连接被回收时是否打印程序的stack traces日志（默认为false，未调整）
	private boolean logAbandoned=true;
	//==数据库连接回收机制==
	
	
	//序列表名称
	private String sequenceTable="sys_sequences";
	public DBCPDataSourceConfig() {
		super();
	}
	public String getDataSourceName() {
		return dataSourceName;
	}
	public void setDataSourceName(String dataSourceName) {
		this.dataSourceName = dataSourceName;
	}
	public String getDriverClassName() {
		return driverClassName;
	}
	public void setDriverClassName(String driverClassName) {
		this.driverClassName = driverClassName;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getConnectionProperties() {
		return connectionProperties;
	}
	public void setConnectionProperties(String connectionProperties) {
		this.connectionProperties = connectionProperties;
	}
	public int getInitialSize() {
		return initialSize;
	}
	public void setInitialSize(int initialSize) {
		this.initialSize = initialSize;
	}
	public int getMaxActive() {
		return maxActive;
	}
	public void setMaxActive(int maxActive) {
		this.maxActive = maxActive;
	}
	public int getMaxIdle() {
		return maxIdle;
	}
	public void setMaxIdle(int maxIdle) {
		this.maxIdle = maxIdle;
	}
	public int getMaxWait() {
		return maxWait;
	}
	public void setMaxWait(int maxWait) {
		this.maxWait = maxWait;
	}
	public boolean isDefaultAutoCommit() {
		return defaultAutoCommit;
	}
	public void setDefaultAutoCommit(boolean defaultAutoCommit) {
		this.defaultAutoCommit = defaultAutoCommit;
	}
	public boolean isTestWhileIdle() {
		return testWhileIdle;
	}
	public void setTestWhileIdle(boolean testWhileIdle) {
		this.testWhileIdle = testWhileIdle;
	}
	public boolean isTestOnBorrow() {
		return testOnBorrow;
	}
	public void setTestOnBorrow(boolean testOnBorrow) {
		this.testOnBorrow = testOnBorrow;
	}
	public boolean isTestOnReturn() {
		return testOnReturn;
	}
	public void setTestOnReturn(boolean testOnReturn) {
		this.testOnReturn = testOnReturn;
	}
	public String getValidationQuery() {
		return validationQuery;
	}
	public void setValidationQuery(String validationQuery) {
		this.validationQuery = validationQuery;
	}
	public int getTimeBetweenEvictionRunsMillis() {
		return timeBetweenEvictionRunsMillis;
	}
	public void setTimeBetweenEvictionRunsMillis(int timeBetweenEvictionRunsMillis) {
		this.timeBetweenEvictionRunsMillis = timeBetweenEvictionRunsMillis;
	}
	public int getNumTestsPerEvictionRun() {
		return numTestsPerEvictionRun;
	}
	public void setNumTestsPerEvictionRun(int numTestsPerEvictionRun) {
		this.numTestsPerEvictionRun = numTestsPerEvictionRun;
	}
	public boolean isRemoveAbandoned() {
		return removeAbandoned;
	}
	public void setRemoveAbandoned(boolean removeAbandoned) {
		this.removeAbandoned = removeAbandoned;
	}
	public int getRemoveAbandonedTimeout() {
		return removeAbandonedTimeout;
	}
	public void setRemoveAbandonedTimeout(int removeAbandonedTimeout) {
		this.removeAbandonedTimeout = removeAbandonedTimeout;
	}
	public boolean isLogAbandoned() {
		return logAbandoned;
	}
	public void setLogAbandoned(boolean logAbandoned) {
		this.logAbandoned = logAbandoned;
	}
	public String getSequenceTable() {
		return sequenceTable;
	}
	public void setSequenceTable(String sequenceTable) {
		this.sequenceTable = sequenceTable;
	}
	
}
