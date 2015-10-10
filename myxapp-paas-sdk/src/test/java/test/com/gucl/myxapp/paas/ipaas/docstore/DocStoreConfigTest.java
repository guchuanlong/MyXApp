package test.com.gucl.myxapp.paas.ipaas.docstore;

import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.myunihome.myxapp.paas.MyXAppConfHelper;
import com.myunihome.myxapp.paas.constants.MyXAppConfConstants;
import com.myunihome.myxapp.paas.model.DocStoreConfigInfo;
import com.myunihome.myxapp.paas.uniconfig.UniConfigFactory;

public class DocStoreConfigTest {
	String path=MyXAppConfConstants.PAAS_DOCSTORE_CONFIG_PATH;
	@Test
	public void testWriteDocStoreConfig(){
		
		StringBuilder sb=new StringBuilder();
		sb.append("{");
		sb.append(" \"mongoDBHostAndPorts\":\"127.0.0.1:27017\", ");
		sb.append("	\"mongoDBDataBaseName\":\"foobar\", ");
		sb.append(" \"mongoDBUserName\":\"foobaruser\",");
		sb.append("	\"mongoDBPassword\":\"foobaruser\",");
		sb.append(" \"redisHostAndPorts\":\"192.168.0.10:6379\",");
		sb.append("	\"mongoDBGridFSBucket\":\"mygridfs01\",");
		sb.append(" \"mongoDBGridFSFileLimitSize\":\"1024\",");
		sb.append("	\"mongoDBGridFSMaxSize\":\"1\" ");
		sb.append("}");
		String data=sb.toString();
		UniConfigFactory.getUniConfigClient().add(path, data);
		System.out.println("写入数据完毕["+path+"]");
		System.out.println("写入验证回读["+path+"]，data="+queryConfig());
		formatByMyAppConfHelper();
	}
	@Test
	public void testDocStoreConfigStrRead(){
		System.out.println("查询数据["+path+"]，data="+queryConfig());
		formatByMyAppConfHelper();
	}
	
	@Test
	public void testDocStoreConfigModify(){
		StringBuilder sb=new StringBuilder();
		sb.append("{");
		sb.append(" \"mongoDBHostAndPorts\":\"127.0.0.1:27017\", ");
		sb.append("	\"mongoDBDataBaseName\":\"foobar\", ");
		sb.append(" \"mongoDBUserName\":\"foobaruser\",");
		sb.append("	\"mongoDBPassword\":\"foobaruser\",");
		sb.append(" \"redisHostAndPorts\":\"192.168.0.10:7001,192.168.0.10:7002,192.168.0.10:7003,192.168.0.10:7004,192.168.0.10:7005,192.168.0.10:7006\",");
		sb.append("	\"mongoDBGridFSBucket\":\"mygridfs01\",");
		sb.append(" \"mongoDBGridFSFileLimitSize\":\"2048\",");
		sb.append("	\"mongoDBGridFSMaxSize\":\"2\" ");
		sb.append("}");
		String data=sb.toString();
		System.out.println("修改前数据["+path+"]，data="+queryConfig());
		UniConfigFactory.getUniConfigClient().modify(path, data);
		System.out.println("修改数据完毕["+path+"]");
		System.out.println("修改后数据["+path+"]，data="+queryConfig());
		
		formatByMyAppConfHelper();
	}
	
	private String queryConfig(){
		return UniConfigFactory.getUniConfigClient().get(path);
	}
	public void formatByMyAppConfHelper(){
		DocStoreConfigInfo config=MyXAppConfHelper.getInstance().getDocStoreConfig();
		System.out.println("config="+JSON.toJSONString(config));
	}
	
}
