package com.myunihome.myxapp.paas.search.vo;

import java.io.Serializable;
import java.util.List;

public class SearchfieldVo implements Serializable {
	private static final long serialVersionUID = 9152243091714512036L;
	private String filedName;
	private List<String> filedValue;
	private SearchOption option;

	public String getFiledName() {
		return this.filedName;
	}

	public void setFiledName(String filedName) {
		this.filedName = filedName;
	}

	public List<String> getFiledValue() {
		return this.filedValue;
	}

	public void setFiledValue(List<String> filedValue) {
		this.filedValue = filedValue;
	}

	public SearchOption getOption() {
		return this.option;
	}

	public void setOption(SearchOption option) {
		this.option = option;
	}
}