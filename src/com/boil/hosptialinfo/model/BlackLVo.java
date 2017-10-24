package com.boil.hosptialinfo.model;

import java.io.Serializable;

public class BlackLVo implements Serializable {
	
	private String resultCode;
	
	private String resultContent;
	
	private String cancelCount;

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getResultContent() {
		return resultContent;
	}

	public void setResultContent(String resultContent) {
		this.resultContent = resultContent;
	}

	public String getCancelCount() {
		return cancelCount;
	}

	public void setCancelCount(String cancelCount) {
		this.cancelCount = cancelCount;
	}

	public BlackLVo() {
		super();
	}
}
