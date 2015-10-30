package com.myunihome.myxapp.paas.model;

import java.io.Serializable;

public class HikariCPDataSourceConfig implements Serializable{
	private static final long serialVersionUID = -3664929924801784994L;
	/* 数据库驱动名称
	 * ORACLE :oracle.jdbc.driver.OracleDriver
	 * MYSQL :com.mysql.jdbc.Driver
	 * SQLSERVER ：com.microsoft.sqlserver.jdbc.SQLServerDriver
	 * DB2 ： com.ibm.db2.jcc.DB2Driver
	 * */
	private String driverClassName;//数据库驱动名称
    private String jdbcUrl;//数据库连接串
	private String username;//用户名
	private String password;//密码
	private boolean autoCommit;//事务自动提交标识
	private int connectionTimeout;// 连接超时时间（milliseconds）
	private int idleTimeout;//连接空闲超时时间（milliseconds）
	private int maxLifetime;//连接最长时间（milliseconds）
	private int minimumIdle;//最小空闲连接数
	private int maximumPoolSize;//连接池最大连接数
	
	private String sequenceTable="sys_sequences";//序列表名称
	public HikariCPDataSourceConfig() {
		super();
	}
	
	public String getDriverClassName() {
		return driverClassName;
	}
	public void setDriverClassName(String driverClassName) {
		this.driverClassName = driverClassName;
	}
	public String getJdbcUrl() {
		return jdbcUrl;
	}
	public void setJdbcUrl(String jdbcUrl) {
		this.jdbcUrl = jdbcUrl;
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
	public boolean isAutoCommit() {
		return autoCommit;
	}
	public void setAutoCommit(boolean autoCommit) {
		this.autoCommit = autoCommit;
	}
	public int getConnectionTimeout() {
		return connectionTimeout;
	}
	public void setConnectionTimeout(int connectionTimeout) {
		this.connectionTimeout = connectionTimeout;
	}
	public int getIdleTimeout() {
		return idleTimeout;
	}
	public void setIdleTimeout(int idleTimeout) {
		this.idleTimeout = idleTimeout;
	}
	public int getMaxLifetime() {
		return maxLifetime;
	}
	public void setMaxLifetime(int maxLifetime) {
		this.maxLifetime = maxLifetime;
	}
	public int getMinimumIdle() {
		return minimumIdle;
	}
	public void setMinimumIdle(int minimumIdle) {
		this.minimumIdle = minimumIdle;
	}
	public int getMaximumPoolSize() {
		return maximumPoolSize;
	}
	public void setMaximumPoolSize(int maximumPoolSize) {
		this.maximumPoolSize = maximumPoolSize;
	}
	public String getSequenceTable() {
		return sequenceTable;
	}
	public void setSequenceTable(String sequenceTable) {
		this.sequenceTable = sequenceTable;
	}
	
	
	
}
