package test.com.gucl.myxapp.paas.ipaas.uniconfig;

import org.junit.Test;

import com.myunihome.myxapp.paas.uniconfig.ZkAdminFactory;

public class ZkAdminTest {
	String path="/zkadmin/cache/etc";
	@Test
	public void testUniConfigAdd(){
		String data="Hello,Zookeeper!";
		ZkAdminFactory.getZkAdminClient().add(path, data);
		System.out.println("写入数据完毕["+path+"]");
		System.out.println("写入验证回读["+path+"]，data="+queryConfig());
	}
	@Test
	public void testUniConfigRead(){
		String pathData=ZkAdminFactory.getZkAdminClient().get(path);
		System.out.println("pathData["+path+"]="+pathData);
	}
	@Test
	public void testUniConfigModify(){
		String data="Hello,Zookeeper!Join in JavaEE Home.";
		System.out.println("修改前数据["+path+"]，data="+queryConfig());
		ZkAdminFactory.getZkAdminClient().modify(path, data);
		System.out.println("修改数据完毕["+path+"]");
		System.out.println("修改后数据["+path+"]，data="+queryConfig());
	}
	
	private String queryConfig(){
		return ZkAdminFactory.getZkAdminClient().get(path);
	}
}
