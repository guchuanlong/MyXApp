package test.com.gucl.myxapp.paas.ipaas.mail;

import org.junit.Test;

import com.myunihome.myxapp.paas.mail.EmailFactory;
import com.myunihome.myxapp.paas.mail.EmailTemplateUtil;

public class MailTest {

	public static final String BIND_EMAIL = "email/template/uac-register-binemail.xml";

	@Test
	public void testSendBindEmail() {
		String[] tomails = new String[] { "gucl@asiainfo.com" };
		String[] ccmails = new String[] { "guchuanlong@126.com" };
		String subject = "myunihome邮件测试";
		String[] data = new String[] { "我的家园nickname", "587434" ,"30"};
		String htmlcontext = EmailTemplateUtil.buildHtmlTextFromTemplate(BIND_EMAIL, data);
		
		try {
			EmailFactory.SendEmail(tomails, ccmails, subject, htmlcontext);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
