package com.myunihome.myxapp.paas.sequence.datasource;

import com.myunihome.myxapp.paas.sequence.exception.SequenceRuntimeException;

public final class SeqDataSourceLoaderFactory {
    
    private SeqDataSourceLoaderFactory(){
        
    }

    private static SeqDataSourceLoader dsLoader;

    public static void init(SeqDataSourceLoader loader) {
        dsLoader = loader;
    }

    public static SeqDataSourceLoader getSeqDsLoader() {
        if (dsLoader == null) {
            throw new SequenceRuntimeException("未初始化SEQ数据源");
        }
        return dsLoader;
    }

}
