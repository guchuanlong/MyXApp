package test.com.gucl.myxapp.paas.ipaas.dubbo;

import org.junit.Ignore;
import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.myunihome.myxapp.paas.MyXAppConfHelper;
import com.myunihome.myxapp.paas.constants.MyXAppConfConstants;
import com.myunihome.myxapp.paas.uniconfig.UniConfigFactory;

public class DubboConsumerConfigTest {
	String dubboConsumerPath=MyXAppConfConstants.DUBBO_CONSUMER_CONF_PATH;
	@Test
	@Ignore
	public void testWriteDubboConsumerConfig(){
		
		StringBuilder sb=new StringBuilder();
		sb.append("{");
		sb.append("	  \"default.dubbo.registry.address\":\"localhost:2181\",");
		sb.append("   \"myxapp-common.registry.address\":\"localhost:2181\",");
		sb.append("	  \"myxapp-sys.registry.address\":\"localhost:2181\" ");
		sb.append("}");
		String data=sb.toString();
		UniConfigFactory.getUniConfigClient().add(dubboConsumerPath, data);
		System.out.println("写入数据完毕["+dubboConsumerPath+"]");
		System.out.println("写入验证回读["+dubboConsumerPath+"]，data="+queryDubboConsumerConfig());
	}
	@Test
	@Ignore
	public void testReadDubboConsumerConfig(){
		System.out.println("查询数据["+dubboConsumerPath+"]，data="+queryDubboConsumerConfig());
	}
	
	@Test
	@Ignore
	public void testModifyDubboConsumerConfig(){
		StringBuilder sb=new StringBuilder();
		sb.append("{");
		sb.append("	  \"default.dubbo.registry.address\":\"localhost:2181\",");
		sb.append("   \"myxapp-common.registry.address\":\"localhost:2181\",");
		sb.append("	  \"myxapp-sys.registry.address\":\"localhost:2181\" ");
		sb.append("}");
		String data=sb.toString();
		System.out.println("修改前数据["+dubboConsumerPath+"]，data="+queryDubboConsumerConfig());
		UniConfigFactory.getUniConfigClient().modify(dubboConsumerPath, data);
		System.out.println("修改数据完毕["+dubboConsumerPath+"]");
		System.out.println("修改后数据["+dubboConsumerPath+"]，data="+queryDubboConsumerConfig());
		
	}
	
	private String queryDubboConsumerConfig(){
		JSONObject config=MyXAppConfHelper.getInstance().getDubboConsumerConf();
		String res=JSON.toJSONString(config);
		return res;
	}
	
}
