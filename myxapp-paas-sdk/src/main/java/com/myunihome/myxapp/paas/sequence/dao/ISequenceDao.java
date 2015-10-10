package com.myunihome.myxapp.paas.sequence.dao;

import java.util.List;

import com.myunihome.myxapp.paas.sequence.model.Sequence;
import com.myunihome.myxapp.paas.sequence.model.SequenceCache;

public interface ISequenceDao {

    SequenceCache getSequenceCache(String sequenceName);

    List<Sequence> queryAllSequence();

    Sequence querySequenceByName(String sequenceName);

    void modifySequence(String sequenceName, long nextVal);

}
