package test.com.gucl.myxapp.paas.ipaas.dubbo;

import org.junit.Ignore;
import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.myunihome.myxapp.paas.MyXAppConfHelper;
import com.myunihome.myxapp.paas.constants.MyXAppConfConstants;
import com.myunihome.myxapp.paas.uniconfig.UniConfigFactory;

public class DubboProviderConfigTest {
	String dubboProviderPath=MyXAppConfConstants.DUBBO_PROVIDER_CONF_PATH;
	@Test
	@Ignore
	public void testWriteDubboProviderConfig(){
		
		StringBuilder sb=new StringBuilder();
		sb.append("{");
		sb.append("	  \"dubbo.provider.retries\":\"0\",");
		sb.append("   \"dubbo.registry.address\":\"localhost:2181\",");
		sb.append("	  \"dubbo.provider.timeout\":\"30000\" ");
		sb.append("}");
		String data=sb.toString();
		UniConfigFactory.getUniConfigClient().add(dubboProviderPath, data);
		System.out.println("写入数据完毕["+dubboProviderPath+"]");
		System.out.println("写入验证回读["+dubboProviderPath+"]，data="+queryDubboProviderConfig());
	}
	@Test
	@Ignore
	public void testReadDubboProviderConfig(){
		System.out.println("查询数据["+dubboProviderPath+"]，data="+queryDubboProviderConfig());
	}
	
	@Test
	@Ignore
	public void testModifyDubboProviderConfig(){
		StringBuilder sb=new StringBuilder();
		sb.append("{");
		sb.append("	  \"dubbo.provider.retries\":\"0\",");
		sb.append("   \"dubbo.registry.address\":\"localhost:2181\",");
		sb.append("	  \"dubbo.provider.timeout\":\"30000\" ");
		sb.append("}");
		String data=sb.toString();
		System.out.println("修改前数据["+dubboProviderPath+"]，data="+queryDubboProviderConfig());
		UniConfigFactory.getUniConfigClient().modify(dubboProviderPath, data);
		System.out.println("修改数据完毕["+dubboProviderPath+"]");
		System.out.println("修改后数据["+dubboProviderPath+"]，data="+queryDubboProviderConfig());
		
	}
	
	private String queryDubboProviderConfig(){
		JSONObject config=MyXAppConfHelper.getInstance().getDubboProviderConf();
		String res=JSON.toJSONString(config);
		return res;
	}
	
}
