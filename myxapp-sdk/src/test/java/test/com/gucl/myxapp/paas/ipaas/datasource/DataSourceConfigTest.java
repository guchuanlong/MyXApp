package test.com.gucl.myxapp.paas.ipaas.datasource;

import java.util.Map;

import org.junit.Ignore;
import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.myunihome.myxapp.paas.MyXAppConfHelper;
import com.myunihome.myxapp.paas.constants.MyXAppConfConstants;
import com.myunihome.myxapp.paas.model.HikariCPDataSourceConfig;
import com.myunihome.myxapp.paas.uniconfig.UniConfigFactory;

public class DataSourceConfigTest {
	String dbPath=MyXAppConfConstants.PAAS_DATASOURCE_HIKARICP_CONFIG_PATH;
	@Test
	public void testWriteDataSourceConfig(){
		
		StringBuilder sb=new StringBuilder();
		sb.append("{  ");
		sb.append("     \"common-center-db\":  ");
		sb.append("		{");
		sb.append("	  		\"driverClassName\":\"com.mysql.jdbc.Driver\",");
		sb.append("	  		\"jdbcUrl\":\"jdbc:mysql://localhost:3306/devibssgndb1?useUnicode=true&characterEncoding=UTF-8\",");
		sb.append("   		\"username\":\"devibssgnusr1\",");
		sb.append("   		\"password\":\"devibssgnusr1\",");
		sb.append("   		\"autoCommit\":\"true\",");
		sb.append("   		\"connectionTimeout\":\"30000\",");
		sb.append("   		\"idleTimeout\":\"600000\",");
		sb.append("   		\"maxLifetime\":\"1800000\",");
		sb.append("   		\"minimumIdle\":\"1\",");
		sb.append("   		\"maximumPoolSize\":\"100\",");
		sb.append("	  		\"sequenceTable\":\"sys_sequences\" ");
		sb.append("		},");
		sb.append("     \"sys-center-db\":  ");
		sb.append("		{");
		sb.append("	  		\"driverClassName\":\"com.mysql.jdbc.Driver\",");
		sb.append("   		\"jdbcUrl\":\"jdbc:mysql://localhost:3306/devibsssadb1?useUnicode=true&characterEncoding=UTF-8\",");
		sb.append("   		\"username\":\"devibsssausr1\",");
		sb.append("   		\"password\":\"devibsssausr1\",");
		sb.append("   		\"autoCommit\":\"true\",");
		sb.append("   		\"connectionTimeout\":\"30000\",");
		sb.append("   		\"idleTimeout\":\"600000\",");
		sb.append("   		\"maxLifetime\":\"1800000\",");
		sb.append("   		\"minimumIdle\":\"1\",");
		sb.append("   		\"maximumPoolSize\":\"100\",");
		sb.append("	  		\"sequenceTable\":\"sys_sequences\" ");
		sb.append("		}");
		sb.append("}");
		String data=sb.toString();
		UniConfigFactory.getUniConfigClient().add(dbPath, data);
		System.out.println("写入数据完毕["+dbPath+"]");
		System.out.println("写入验证回读["+dbPath+"]，data="+queryDataSourceConfig());
	}
	@Test
	@Ignore
	public void testReadDataSourceConfig(){
		System.out.println("查询数据["+dbPath+"]，data="+queryDataSourceConfig());
	}
	
	@Test
	@Ignore
	public void testModifyDataSourceConfig(){
		StringBuilder sb=new StringBuilder();
		sb.append("{  ");
		sb.append("     \"common-center-db\":  ");
		sb.append("		{");
		sb.append("	  		\"driverClassName\":\"com.mysql.jdbc.Driver\",");
		sb.append("	  		\"jdbcUrl\":\"jdbc:mysql://localhost:3306/devibssgndb1?useUnicode=true&characterEncoding=UTF-8\",");
		sb.append("   		\"username\":\"devibssgnusr1\",");
		sb.append("   		\"password\":\"devibssgnusr1\",");
		sb.append("   		\"autoCommit\":\"true\",");
		sb.append("   		\"connectionTimeout\":\"30000\",");
		sb.append("   		\"idleTimeout\":\"600000\",");
		sb.append("   		\"maxLifetime\":\"1800000\",");
		sb.append("   		\"minimumIdle\":\"1\",");
		sb.append("   		\"maximumPoolSize\":\"100\",");
		sb.append("	  		\"sequenceTable\":\"sys_sequences\" ");
		sb.append("		},");
		sb.append("     \"sys-center-db\":  ");
		sb.append("		{");
		sb.append("	  		\"driverClassName\":\"com.mysql.jdbc.Driver\",");
		sb.append("   		\"jdbcUrl\":\"jdbc:mysql://localhost:3306/devibsssadb1?useUnicode=true&characterEncoding=UTF-8\",");
		sb.append("   		\"username\":\"devibsssausr1\",");
		sb.append("   		\"password\":\"devibsssausr1\",");
		sb.append("   		\"autoCommit\":\"true\",");
		sb.append("   		\"connectionTimeout\":\"30000\",");
		sb.append("   		\"idleTimeout\":\"600000\",");
		sb.append("   		\"maxLifetime\":\"1800000\",");
		sb.append("   		\"minimumIdle\":\"1\",");
		sb.append("   		\"maximumPoolSize\":\"100\",");
		sb.append("	  		\"sequenceTable\":\"sys_sequences\" ");
		sb.append("		}");
		sb.append("}");
		String data=sb.toString();
		System.out.println("修改前数据["+dbPath+"]，data="+queryDataSourceConfig());
		UniConfigFactory.getUniConfigClient().modify(dbPath, data);
		System.out.println("修改数据完毕["+dbPath+"]");
		System.out.println("修改后数据["+dbPath+"]，data="+queryDataSourceConfig());
		
	}
	
	private String queryDataSourceConfig(){
		Map<String,HikariCPDataSourceConfig> config=MyXAppConfHelper.getInstance().getHikariCPDataSourceConfigMap();
		String res=JSON.toJSONString(config);
		return res;
	}
	
}
