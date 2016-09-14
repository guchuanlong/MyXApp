package com.myunihome.myxapp.paas.datasource;

import org.apache.commons.dbcp.BasicDataSource;

import com.myunihome.myxapp.paas.MyXAppConfHelper;
import com.myunihome.myxapp.paas.constants.MyXAppPaaSConstant;
import com.myunihome.myxapp.paas.model.DBCPDataSourceConfig;
import com.myunihome.myxapp.paas.util.BeanUtils;

public class MyDBCPDataSource extends BasicDataSource {
	public MyDBCPDataSource(){
		this(MyXAppPaaSConstant.DEFAULT);
	}
	public MyDBCPDataSource(String dataSourceName) {
		super();
		//获取数据库配置信息
		DBCPDataSourceConfig conf=MyXAppConfHelper.getInstance().getDBCPDataSourceConfig(dataSourceName);
		//设置数据源连接属性
		BeanUtils.copyProperties(this, conf);	
	}
	

}
