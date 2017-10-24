package com.boil.hospitalorder.hospitaldoctor.main.homepage.model;

import java.io.Serializable;

public class CheckMessageVo implements Serializable{
	
	private String createTime;
	private String desc;
	private String hisId;
	private String status;
	private String icddxDesc;
	private String icodxCode;
	
	public CheckMessageVo() {
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getHisId() {
		return hisId;
	}

	public void setHisId(String hisId) {
		this.hisId = hisId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getIcddxDesc() {
		return icddxDesc;
	}

	public void setIcddxDesc(String icddxDesc) {
		this.icddxDesc = icddxDesc;
	}

	public String getIcodxCode() {
		return icodxCode;
	}

	public void setIcodxCode(String icodxCode) {
		this.icodxCode = icodxCode;
	}
	
	
}
