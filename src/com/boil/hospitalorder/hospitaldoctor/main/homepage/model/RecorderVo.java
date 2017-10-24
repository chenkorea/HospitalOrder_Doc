package com.boil.hospitalorder.hospitaldoctor.main.homepage.model;

import java.io.Serializable;

public class RecorderVo implements Serializable{

	// 就诊科室
	private String admDep;
	// 就诊号
	private String admNo;
	// 就诊状态
	private String admSta;
	//
	private String admTime;
	private String admType;
	// 是否可以查看明细
	private String seeFlag;
	
	public RecorderVo() {
	}
	
	public RecorderVo(String admDep, String admNo, String admSta, String admTime, String admType, String seeFlag) {
		super();
		this.admDep = admDep;
		this.admNo = admNo;
		this.admSta = admSta;
		this.admTime = admTime;
		this.admType = admType;
		this.seeFlag = seeFlag;
	}
	public String getAdmDep() {
		return admDep;
	}
	public void setAdmDep(String admDep) {
		this.admDep = admDep;
	}
	public String getAdmNo() {
		return admNo;
	}
	public void setAdmNo(String admNo) {
		this.admNo = admNo;
	}
	public String getAdmSta() {
		return admSta;
	}
	public void setAdmSta(String admSta) {
		this.admSta = admSta;
	}
	public String getAdmTime() {
		return admTime;
	}
	public void setAdmTime(String admTime) {
		this.admTime = admTime;
	}
	public String getAdmType() {
		return admType;
	}
	public void setAdmType(String admType) {
		this.admType = admType;
	}
	public String getSeeFlag() {
		return seeFlag;
	}
	public void setSeeFlag(String seeFlag) {
		this.seeFlag = seeFlag;
	}
	
	
}
