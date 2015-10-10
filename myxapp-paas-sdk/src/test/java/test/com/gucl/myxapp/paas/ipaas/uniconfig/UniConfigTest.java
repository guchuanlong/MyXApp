package test.com.gucl.myxapp.paas.ipaas.uniconfig;

import org.junit.Test;

import com.myunihome.myxapp.paas.uniconfig.UniConfigFactory;

public class UniConfigTest {
	String path="/cache/etc";
	@Test
	public void testUniConfigAdd(){
		String data="Hello,Zookeeper!";
		UniConfigFactory.getUniConfigClient().add(path, data);
		System.out.println("写入数据完毕["+path+"]");
		System.out.println("写入验证回读["+path+"]，data="+queryConfig());
	}
	@Test
	public void testUniConfigRead(){
		System.out.println("查询数据["+path+"]，data="+queryConfig());
	}
	@Test
	public void testUniConfigModify(){
		String data="Hello,Zookeeper!Join in JavaEE Home.";
		System.out.println("修改前数据["+path+"]，data="+queryConfig());
		UniConfigFactory.getUniConfigClient().modify(path, data);
		System.out.println("修改数据完毕["+path+"]");
		System.out.println("修改后数据["+path+"]，data="+queryConfig());
	}
	
	private String queryConfig(){
		return UniConfigFactory.getUniConfigClient().get(path);
	}
}
