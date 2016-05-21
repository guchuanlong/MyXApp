package com.myunihome.myxapp.paas.unisession.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.myunihome.myxapp.paas.unisession.RequestEventObserver;


public class RequestEventSubject {
    private RequestEventObserver listener;

    public void attach(RequestEventObserver eventObserver) {
        this.listener = eventObserver;
    }

    public void detach() {
        this.listener = null;
    }

    public void completed(HttpServletRequest servletRequest,
                          HttpServletResponse response) {
        if (this.listener != null)
            this.listener.completed(servletRequest, response);
    }
}
