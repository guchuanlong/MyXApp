package com.myunihome.myxapp.paas.sequence;

import com.myunihome.myxapp.paas.sequence.factory.SequenceFactory;
import com.myunihome.myxapp.paas.util.StringUtil;

public final class SeqUtil {

    private SeqUtil() {

    }

    public static Long getNewId(String seqName) {
        return SequenceFactory.getSeqClient().nextValue(seqName);
    }

    public static String getNewId(String seqName, int seqLen) {
        Long newId = getNewId(seqName);
        String seqStr = StringUtil.toString(newId);
        while (seqStr.length() < seqLen) {
            seqStr = "0000000" + seqStr;
        }
        return seqStr.substring(seqStr.length() - seqLen);
    }

}
