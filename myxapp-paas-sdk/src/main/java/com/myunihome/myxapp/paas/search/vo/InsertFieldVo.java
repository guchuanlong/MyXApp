package com.myunihome.myxapp.paas.search.vo;

import java.io.Serializable;

public class InsertFieldVo implements Serializable {
	private static final long serialVersionUID = 9152243091714512036L;
	private String filedName;
	private Object filedValue;
	private FiledType fileType;

	public InsertFieldVo() {
		this.fileType = FiledType.string;
	}

	public String getFiledName() {
		return this.filedName;
	}

	public void setFiledName(String filedName) {
		this.filedName = filedName;
	}

	public Object getFiledValue() {
		return this.filedValue;
	}

	public void setFiledValue(Object filedValue) {
		this.filedValue = filedValue;
	}

	public FiledType getFileType() {
		return this.fileType;
	}

	public void setFileType(FiledType fileType) {
		this.fileType = fileType;
	}

	public static enum FiledType {
		string,
		completion;
	}
}