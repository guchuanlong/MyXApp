package test.com.gucl.myxapp.paas.ipaas.jvm;

import org.junit.Ignore;
import org.junit.Test;

public class TestJVM {
	@Test
	@Ignore
	public void testJVMSysArgs(){
		System.getProperties().list(System.out);
	}
}
