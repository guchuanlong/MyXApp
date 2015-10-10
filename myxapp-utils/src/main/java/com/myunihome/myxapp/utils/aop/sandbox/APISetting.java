package com.myunihome.myxapp.utils.aop.sandbox;

public class APISetting {

    public static final String CALL_SANDBOX = "1";

    private String callType;

    private String sandboxResp;

    public String getCallType() {
        return callType;
    }

    public void setCallType(String callType) {
        this.callType = callType;
    }

    public String getSandboxResp() {
        return sandboxResp;
    }

    public void setSandboxResp(String sandboxResp) {
        this.sandboxResp = sandboxResp;
    }

}
