package com.myunihome.myxapp.paas.search.vo;

import java.util.List;

public class Results<T> {
	private List<T> searchList;
	private long count;
	private String resultCode;

	public String getResultCode() {
		return this.resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public List<T> getSearchList() {
		return this.searchList;
	}

	public void setSearchList(List<T> searchList) {
		this.searchList = searchList;
	}

	public long getCount() {
		return this.count;
	}

	public void setCounts(long count) {
		this.count = count;
	}
}