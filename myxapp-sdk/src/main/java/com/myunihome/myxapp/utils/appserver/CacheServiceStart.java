package com.myunihome.myxapp.utils.appserver;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.myunihome.myxapp.utils.cache.base.AbstractCache;

public final class CacheServiceStart {

    private static final Log LOG = LogFactory.getLog(CacheServiceStart.class);

    private static final String PATH = "classpath:context/core-context.xml";
    private CacheServiceStart(){}
    public static void main(String[] args) {
        LOG.info("开始刷新缓存......");
        @SuppressWarnings("resource")
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                new String[] { PATH });
        context.start();
        Map<String, AbstractCache> caches = context
                .getBeansOfType(AbstractCache.class);
        for (AbstractCache cache : caches.values()) {
            try {
                cache.write();
            } catch (Exception ex) {
                LOG.error("缓存写入失败",ex);
            }

        }
        // 刷新缓存结束，需要提示
        LOG.info("缓存刷新结束,请通过日志查看是否刷新成功.....");
    }
}
