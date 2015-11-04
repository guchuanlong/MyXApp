package test.com.gucl.myxapp.paas.ipaas.cache;

import org.junit.Ignore;
import org.junit.Test;

import com.myunihome.myxapp.paas.cache.CacheFactory;

public class CacheTest {
	String key="gucl.com.myapp-sso.code_area";
	String field="11";
	String value="北京";
	@Test
	@Ignore
	public void testWriteCache(){
		CacheFactory.getCacheClient().hset(key, field, value);
		System.out.println("写入数据完毕["+key+"]");
		System.out.println("写入验证回读["+key+"]，data="+queryCache());
	}
	@Test
	@Ignore
	public void testCacheRead(){
		System.out.println("查询数据["+key+"]，data="+queryCache());
	}
	
	@Test
	@Ignore
	public void testCacheModify(){
		value="北京大兴11223344";
		System.out.println("修改前数据["+key+"]，data="+queryCache());
		CacheFactory.getCacheClient().hset(key, field, value);
		System.out.println("修改数据完毕["+key+"]");
		System.out.println("修改后数据["+key+"]，data="+queryCache());
		
	}
	
	private String queryCache(){
		return CacheFactory.getCacheClient().hget(key, field);
	}
	
}
