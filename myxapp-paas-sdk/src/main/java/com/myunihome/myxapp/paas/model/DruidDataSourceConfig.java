package com.myunihome.myxapp.paas.model;

import java.io.Serializable;

public class DruidDataSourceConfig implements Serializable{
	private static final long serialVersionUID = -3664929924801784994L;
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
	
	private String filters="stat";
	
	private int maxActive;
	private int initialSize;
	private int maxWait;
	private int minIdle;
	
	private int timeBetweenEvictionRunsMillis;
	private int minEvictableIdleTimeMillis;
	
	private  String validationQuery="SELECT 'x'";
	private boolean testWhileIdle=true;
	private boolean testOnBorrow=false;
	private boolean testOnReturn=false;
	private boolean poolPreparedStatements=true;
	private int maxPoolPreparedStatementPerConnectionSize=50;
	
	private String sequenceTable="sys_sequences";//序列表名称
	public DruidDataSourceConfig() {
		super();
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
	public String getFilters() {
		return filters;
	}
	public void setFilters(String filters) {
		this.filters = filters;
	}
	public int getMaxActive() {
		return maxActive;
	}
	public void setMaxActive(int maxActive) {
		this.maxActive = maxActive;
	}
	public int getInitialSize() {
		return initialSize;
	}
	public void setInitialSize(int initialSize) {
		this.initialSize = initialSize;
	}
	public int getMaxWait() {
		return maxWait;
	}
	public void setMaxWait(int maxWait) {
		this.maxWait = maxWait;
	}
	public int getMinIdle() {
		return minIdle;
	}
	public void setMinIdle(int minIdle) {
		this.minIdle = minIdle;
	}
	public int getTimeBetweenEvictionRunsMillis() {
		return timeBetweenEvictionRunsMillis;
	}
	public void setTimeBetweenEvictionRunsMillis(int timeBetweenEvictionRunsMillis) {
		this.timeBetweenEvictionRunsMillis = timeBetweenEvictionRunsMillis;
	}
	public int getMinEvictableIdleTimeMillis() {
		return minEvictableIdleTimeMillis;
	}
	public void setMinEvictableIdleTimeMillis(int minEvictableIdleTimeMillis) {
		this.minEvictableIdleTimeMillis = minEvictableIdleTimeMillis;
	}
	public String getValidationQuery() {
		return validationQuery;
	}
	public void setValidationQuery(String validationQuery) {
		this.validationQuery = validationQuery;
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
	public boolean isPoolPreparedStatements() {
		return poolPreparedStatements;
	}
	public void setPoolPreparedStatements(boolean poolPreparedStatements) {
		this.poolPreparedStatements = poolPreparedStatements;
	}
	public int getMaxPoolPreparedStatementPerConnectionSize() {
		return maxPoolPreparedStatementPerConnectionSize;
	}
	public void setMaxPoolPreparedStatementPerConnectionSize(int maxPoolPreparedStatementPerConnectionSize) {
		this.maxPoolPreparedStatementPerConnectionSize = maxPoolPreparedStatementPerConnectionSize;
	}
	public String getSequenceTable() {
		return sequenceTable;
	}
	public void setSequenceTable(String sequenceTable) {
		this.sequenceTable = sequenceTable;
	}
	
	
}
