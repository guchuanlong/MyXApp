package com.myunihome.myxapp.paas.model;

import java.io.Serializable;

public class C3P0DataSourceConfig implements Serializable{
	private static final long serialVersionUID = -3664929924801784994L;
	private String dataSourceName="C3P0";//数据源类型HikariCP|Druid|DBCP|C3P0
	/* 数据库驱动名称
	 * ORACLE :oracle.jdbc.driver.OracleDriver
	 * MYSQL :com.mysql.jdbc.Driver
	 * SQLSERVER ：com.microsoft.sqlserver.jdbc.SQLServerDriver
	 * DB2 ： com.ibm.db2.jcc.DB2Driver
	 * */
	private String driverClass;
	//数据库连接串
    private String jdbcUrl;
    //用户名
	private String user;
	//密码
	private String password;
	//连接池中保留的最小连接数。
	private int minPoolSize=1;
	//连接池中保留的最大连接数。Default: 15
	private int maxPoolSize=100;
	//初始化时获取三个连接，取值应在minPoolSize与maxPoolSize之间。Default: 3
	private int initialPoolSize=3;
	//最大空闲时间,60秒内未使用则连接被丢弃。若为0则永不丢弃。Default: 0
	private int maxIdleTime=0;
	//当连接池中的连接耗尽的时候c3p0一次同时获取的连接数。Default: 3
	private int acquireIncrement=3;
	//定义在从数据库获取新连接失败后重复尝试的次数。Default: 30
	private int acquireRetryAttempts=30;
	//两次连接中间隔时间，单位毫秒。Default: 1000
	private int acquireRetryDelay=1000;
	//如果设为true那么在取得连接的同时将校验连接的有效性。Default: false
	private boolean testConnectionOnCheckin= false;
	//c3p0将建一张名为Test的空表，并使用其自带的查询语句进行测试。如果定义了这个参数那么属性preferredTestQuery将被忽略。你不能在这张Test表上进行任何操作，它将只供c3p0测试使用。Default: null
	private String automaticTestTable="Test";
	//每18000秒检查所有连接池中的空闲连接。Default: 0
	private int idleConnectionTestPeriod=0;	
	//当连接池用完时客户端调用getConnection()后等待获取新连接的时间，超时后将抛出SQLException,如设为0则无限期等待。单位毫秒。Default: 0
	private int checkoutTimeout=0;
	
	//序列表名称
	private String sequenceTable="sys_sequences";
	
	public C3P0DataSourceConfig() {
		super();
	}

	public String getDataSourceName() {
		return dataSourceName;
	}

	public void setDataSourceName(String dataSourceName) {
		this.dataSourceName = dataSourceName;
	}

	public String getDriverClass() {
		return driverClass;
	}

	public void setDriverClass(String driverClass) {
		this.driverClass = driverClass;
	}

	public String getJdbcUrl() {
		return jdbcUrl;
	}

	public void setJdbcUrl(String jdbcUrl) {
		this.jdbcUrl = jdbcUrl;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getMinPoolSize() {
		return minPoolSize;
	}

	public void setMinPoolSize(int minPoolSize) {
		this.minPoolSize = minPoolSize;
	}

	public int getMaxPoolSize() {
		return maxPoolSize;
	}

	public void setMaxPoolSize(int maxPoolSize) {
		this.maxPoolSize = maxPoolSize;
	}

	public int getInitialPoolSize() {
		return initialPoolSize;
	}

	public void setInitialPoolSize(int initialPoolSize) {
		this.initialPoolSize = initialPoolSize;
	}

	public int getMaxIdleTime() {
		return maxIdleTime;
	}

	public void setMaxIdleTime(int maxIdleTime) {
		this.maxIdleTime = maxIdleTime;
	}

	public int getAcquireIncrement() {
		return acquireIncrement;
	}

	public void setAcquireIncrement(int acquireIncrement) {
		this.acquireIncrement = acquireIncrement;
	}

	public int getAcquireRetryAttempts() {
		return acquireRetryAttempts;
	}

	public void setAcquireRetryAttempts(int acquireRetryAttempts) {
		this.acquireRetryAttempts = acquireRetryAttempts;
	}

	public int getAcquireRetryDelay() {
		return acquireRetryDelay;
	}

	public void setAcquireRetryDelay(int acquireRetryDelay) {
		this.acquireRetryDelay = acquireRetryDelay;
	}

	public boolean isTestConnectionOnCheckin() {
		return testConnectionOnCheckin;
	}

	public void setTestConnectionOnCheckin(boolean testConnectionOnCheckin) {
		this.testConnectionOnCheckin = testConnectionOnCheckin;
	}

	public String getAutomaticTestTable() {
		return automaticTestTable;
	}

	public void setAutomaticTestTable(String automaticTestTable) {
		this.automaticTestTable = automaticTestTable;
	}

	public int getIdleConnectionTestPeriod() {
		return idleConnectionTestPeriod;
	}

	public void setIdleConnectionTestPeriod(int idleConnectionTestPeriod) {
		this.idleConnectionTestPeriod = idleConnectionTestPeriod;
	}

	public int getCheckoutTimeout() {
		return checkoutTimeout;
	}

	public void setCheckoutTimeout(int checkoutTimeout) {
		this.checkoutTimeout = checkoutTimeout;
	}

	public String getSequenceTable() {
		return sequenceTable;
	}

	public void setSequenceTable(String sequenceTable) {
		this.sequenceTable = sequenceTable;
	}
	
	
	
	
}
