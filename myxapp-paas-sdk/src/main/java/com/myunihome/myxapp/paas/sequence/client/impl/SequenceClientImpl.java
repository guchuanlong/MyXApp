package com.myunihome.myxapp.paas.sequence.client.impl;

import com.myunihome.myxapp.paas.sequence.client.ISequenceClient;
import com.myunihome.myxapp.paas.sequence.service.ISequenceService;
import com.myunihome.myxapp.paas.sequence.service.impl.SequenceServiceImpl;

public class SequenceClientImpl implements ISequenceClient {

    private ISequenceService sequenceService;

    public SequenceClientImpl() {
        this.sequenceService = new SequenceServiceImpl();
    }

    @Override
    public Long nextValue(String sequenceName) {
        return sequenceService.nextValue(sequenceName);
    }

}
