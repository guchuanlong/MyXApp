package com.myunihome.myxapp.utils.appserver;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public final class DubboServiceStart {
    
    private static final Logger LOG = LogManager.getLogger(DubboServiceStart.class.getName());

    private static final String DUBBO_CONTEXT = "classpath:dubbo/provider/dubbo-provider.xml";
    
    private DubboServiceStart(){}
    
    @SuppressWarnings("resource")
    private static void startDubbo() {
        LOG.info("开始启动 Dubbo 服务---------------------------");
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                new String[] { DUBBO_CONTEXT });
        context.registerShutdownHook();
        context.start();
        LOG.info(" Dubbo 服务启动完毕---------------------------");
        while (true) {
            try {
                Thread.currentThread();
                Thread.sleep(3000L);
            } catch (Exception e) {
            	LOG.error("Dubbo 系统错误，具体信息为："+e.getMessage(),e);
            }
        }
    }

    public static void main(String[] args) {
        startDubbo();
    }
}
