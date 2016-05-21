package test.com.gucl.myxapp.paas.ipaas.search;

import org.junit.Ignore;
import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.myunihome.myxapp.paas.MyXAppConfHelper;
import com.myunihome.myxapp.paas.constants.MyXAppConfConstants;
import com.myunihome.myxapp.paas.model.ESConfigInfo;
import com.myunihome.myxapp.paas.uniconfig.UniConfigFactory;

public class SearchConfigTest {
	String searchConfigPath=MyXAppConfConstants.PAAS_SEARCH_CONFIG_PATH;
	@Test
	@Ignore
	public void testWriteSearchConfig(){
		
		StringBuilder sb=new StringBuilder();
		sb.append("{");
		sb.append("	   \"default\":");
		sb.append("    {");
		sb.append("	      \"hostAndPorts\":\"127.0.0.1:9300,192.168.0.10:9300\" ");
		sb.append("    }");
		sb.append("}");
		String data=sb.toString();
		UniConfigFactory.getUniConfigClient().add(searchConfigPath, data);
		System.out.println("写入数据完毕["+searchConfigPath+"]");
		System.out.println("写入验证回读["+searchConfigPath+"]，data="+querySearchConfig());
	}
	@Test
	@Ignore
	public void testReadSearchConfig(){
		System.out.println("查询数据["+searchConfigPath+"]，data="+querySearchConfig());
	}
	
	@Test
	@Ignore
	public void testModifySearchConfig(){
		StringBuilder sb=new StringBuilder();
		sb.append("{");
		sb.append("	   \"default\":");
		sb.append("    {");
		sb.append("	      \"hostAndPorts\":\"127.0.0.1:9300\" ");
		sb.append("    }");
		sb.append("}");
		String data=sb.toString();
		System.out.println("修改前数据["+searchConfigPath+"]，data="+querySearchConfig());
		UniConfigFactory.getUniConfigClient().modify(searchConfigPath, data);
		System.out.println("修改数据完毕["+searchConfigPath+"]");
		System.out.println("修改后数据["+searchConfigPath+"]，data="+querySearchConfig());
		
	}
	
	private String querySearchConfig(){
		ESConfigInfo res=MyXAppConfHelper.getInstance().getSearchConfig();
		return JSON.toJSONString(res);
	}
	
}
