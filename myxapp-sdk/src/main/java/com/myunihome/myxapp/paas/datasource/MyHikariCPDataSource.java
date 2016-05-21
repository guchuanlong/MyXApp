package com.myunihome.myxapp.paas.datasource;

import com.myunihome.myxapp.paas.MyXAppConfHelper;
import com.myunihome.myxapp.paas.constants.MyXAppPaaSConstant;
import com.myunihome.myxapp.paas.model.HikariCPDataSourceConfig;
import com.myunihome.myxapp.paas.util.BeanUtils;
import com.zaxxer.hikari.HikariDataSource;

public class MyHikariCPDataSource extends HikariDataSource {
	public MyHikariCPDataSource(){
		this(MyXAppPaaSConstant.DEFAULT);
	}
	public MyHikariCPDataSource(String dataSourceName) {
		super();
		//获取数据库配置信息
		HikariCPDataSourceConfig conf=MyXAppConfHelper.getInstance().getHikariCPDataSourceConfig(dataSourceName);
		//设置数据源连接属性
		BeanUtils.copyProperties(this, conf);	
	}
	

}
