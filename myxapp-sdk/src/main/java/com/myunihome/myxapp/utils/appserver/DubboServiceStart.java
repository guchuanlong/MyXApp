package com.myunihome.myxapp.utils.appserver;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public final class DubboServiceStart {
    
    private static final Log LOG = LogFactory.getLog(DubboServiceStart.class.getName());

    private static final String DUBBO_CONTEXT = "classpath:dubbo/provider/dubbo-provider.xml";
    
    private DubboServiceStart(){}
    
    @SuppressWarnings("resource")
    private static void startDubbo() {
        LOG.info("开始启动 Dubbo 服务---------------------------");
        // 从配置中心加载DUBBO的核心配置
        //DubboPropUtil.setDubboProviderProperties();
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                new String[] { DUBBO_CONTEXT });
        context.registerShutdownHook();
        context.start();
        LOG.info(" Dubbo 服务启动完毕---------------------------");
        synchronized (DubboServiceStart.class) {
        	while (true) {
        		try {
        			//Thread.currentThread();
        			//Thread.sleep(3000L);
        			DubboServiceStart.class.wait();
        		} catch (Exception e) {
        			LOG.error("Dubbo 系统错误，具体信息为："+e.getMessage(),e);
        		}
        	}
		}
    }

    public static void main(String[] args) {
        startDubbo();
    }
}
