package test.com.gucl.myxapp.paas.ipaas.cache;

import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.myunihome.myxapp.paas.MyXAppConfHelper;
import com.myunihome.myxapp.paas.constants.MyXAppConfConstants;
import com.myunihome.myxapp.paas.model.CacheConfigInfo;
import com.myunihome.myxapp.paas.uniconfig.UniConfigFactory;

public class CacheConfigTest {
	String path=MyXAppConfConstants.PAAS_CACHE_CONFIG_PATH;
	@Test
	public void testWriteCacheConfig(){
		
		StringBuilder sb=new StringBuilder();
		sb.append("{");
		sb.append("	\"jedisPoolConfig\":{\"maxTotal\":\"500\",\"maxIdle\":\"5\",\"maxWaitMillis\":\"10000\",\"testOnBorrow\":\"true\"},");
		sb.append(" \"jedisHostAndPorts\":\"127.0.0.1:6379\",");
		sb.append("	\"password\":\"123456\" ");
		sb.append("}");
		String data=sb.toString();
		UniConfigFactory.getUniConfigClient().add(path, data);
		System.out.println("写入数据完毕["+path+"]");
		System.out.println("写入验证回读["+path+"]，data="+queryConfig());
		formatByMyAppConfHelper();
	}
	@Test
	public void testCacheConfigStrRead(){
		System.out.println("查询数据["+path+"]，data="+queryConfig());
		formatByMyAppConfHelper();
	}
	
	@Test
	public void testCacheConfigModify(){
		StringBuilder sb=new StringBuilder();
		sb.append("{");
		sb.append("	\"jedisPoolConfig\":{\"maxTotal\":\"500\",\"maxIdle\":\"5\",\"maxWaitMillis\":\"10000\",\"testOnBorrow\":\"true\"},");
//		sb.append(" \"jedisHostAndPorts\":\"192.168.0.10:6379\",");
		sb.append(" \"jedisHostAndPorts\":\"192.168.0.10:7001,192.168.0.10:7002,192.168.0.10:7003,192.168.0.10:7004,192.168.0.10:7005,192.168.0.10:7006\",");
		sb.append("	\"password\":\"123456\" ");
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
		CacheConfigInfo config=MyXAppConfHelper.getInstance().getCacheConfig();
		System.out.println("config="+JSON.toJSONString(config));
	}
	
}
