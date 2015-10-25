package test.com.gucl.myxapp.paas.ipaas.jvm;

import org.junit.Test;

public class TestJVM {
	@Test
	public void testJVMSysArgs(){
		System.getProperties().list(System.out);
	}
}
