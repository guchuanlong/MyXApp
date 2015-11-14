package com.myunihome.myxapp.paas.model;

import java.io.Serializable;

public class ESConfigInfo implements Serializable{
	private static final long serialVersionUID = 197898012023214708L;
	private String hostAndPorts;//示例：127.0.0.1:9300,192.168.0.10:9300
	public ESConfigInfo() {
		super();
	}
	public ESConfigInfo(String hostAndPorts) {
		super();
		this.hostAndPorts = hostAndPorts;
	}
	public String getHostAndPorts() {
		return hostAndPorts;
	}
	public void setHostAndPorts(String hostAndPorts) {
		this.hostAndPorts = hostAndPorts;
	}
    
}
