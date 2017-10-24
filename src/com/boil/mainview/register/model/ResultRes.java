package com.boil.mainview.register.model;

public class ResultRes {
	/** 返回代码 */
	private String resultCode;
	/** 返回类容 */
	private String resultContent;
	
	public ResultRes() {
		
	}

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

	@Override
	public String toString() {
		return "ResultRes [resultCode=" + resultCode + ", resultContent="
				+ resultContent + "]";
	}
}