package com.myunihome.myxapp.utils.appserver;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public final class RestServiceStart {

    private static final Logger LOG = LogManager.getLogger(RestServiceStart.class.getName());

    private static final String REST_CONTEXT = "classpath:dubbo/provider/rest-provider.xml";

    private RestServiceStart() {
    }

    @SuppressWarnings("resource")
    private static void startRest() {
        LOG.info("开始启动 REST 服务---------------------------");
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                new String[] { REST_CONTEXT });
        context.registerShutdownHook();
        context.start();
        LOG.error(" REST 服务启动完毕---------------------------");
        while (true) {
            try {
                Thread.currentThread();
                Thread.sleep(3000L);
            } catch (Exception e) {
                LOG.error("REST 系统错误，具体信息为：" + e.getMessage(), e);
            }
        }
    }

    public static void main(String[] args) {
        startRest();
    }
}
