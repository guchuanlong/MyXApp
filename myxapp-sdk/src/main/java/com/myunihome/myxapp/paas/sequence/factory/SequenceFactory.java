package com.myunihome.myxapp.paas.sequence.factory;

import com.myunihome.myxapp.paas.sequence.client.ISequenceClient;
import com.myunihome.myxapp.paas.sequence.client.impl.SequenceClientImpl;
import com.myunihome.myxapp.paas.sequence.exception.SequenceRuntimeException;

public final class SequenceFactory {

    private SequenceFactory() {

    }

    // 普通方式提供SEQ获取
    private static final String NORMAL_SEQ_TYPE = "normal_seq";

    // IPAAS的DBS提供SEQ获取
    private static final String IPAAS_DBS_SEQ_TYPE = "ipaas_dbs_seq";

    private static ISequenceClient sequenceClient;

    public static ISequenceClient getSeqClient() {
        if (sequenceClient == null) {
            String seqType = NORMAL_SEQ_TYPE;
            if (NORMAL_SEQ_TYPE.equals(seqType)) {
                sequenceClient = new SequenceClientImpl();
            } else if (IPAAS_DBS_SEQ_TYPE.equals(seqType)) {
                //sequenceClient = new DBSSeqClientImpl();
            } else {
                throw new SequenceRuntimeException("not supported seq type[" + seqType + "]");
            }
        }
        return sequenceClient;
    }
}
