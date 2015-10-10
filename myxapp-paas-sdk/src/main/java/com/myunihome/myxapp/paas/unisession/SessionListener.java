package com.myunihome.myxapp.paas.unisession;


import com.myunihome.myxapp.paas.unisession.impl.CacheHttpSession;

public interface SessionListener {
	
    void onAttributeChanged(CacheHttpSession paramRedisHttpSession);

    void onInvalidated(CacheHttpSession paramRedisHttpSession);
}
