package com.myunihome.myxapp.paas.datasource;

import com.alibaba.druid.pool.DruidDataSource;
import com.myunihome.myxapp.paas.MyXAppConfHelper;
import com.myunihome.myxapp.paas.constants.MyXAppPaaSConstant;
import com.myunihome.myxapp.paas.model.DruidDataSourceConfig;
import com.myunihome.myxapp.paas.util.BeanUtils;

public class MyDruidDataSource extends DruidDataSource {
	private static final long serialVersionUID = -7619248113714004065L;
	public MyDruidDataSource(){
		this(MyXAppPaaSConstant.DEFAULT);
	}
	public MyDruidDataSource(String dataSourceName) {
		super();
		//获取数据库配置信息
		DruidDataSourceConfig conf=MyXAppConfHelper.getInstance().getDruidDataSourceConfig(dataSourceName);
		//设置数据源连接属性
		BeanUtils.copyProperties(this, conf);			
	}
	

}
