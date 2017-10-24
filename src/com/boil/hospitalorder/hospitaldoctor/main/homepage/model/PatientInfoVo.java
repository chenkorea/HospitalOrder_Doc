package com.boil.hospitalorder.hospitaldoctor.main.homepage.model;

import java.io.Serializable;

public class PatientInfoVo implements Serializable{

	private String admId;
	private String admdate;
	private String admno;
	private String bedno;
	private String doc_hisId;
	private String doc_name;
	private String doc_payway;
	private String hisId;
	private String medicare;
	private String name;
	private String register;
	private String state;
	private String age;
	private String sex;
	private String isCPW;
	
	public PatientInfoVo() {
	}
	
	
	public PatientInfoVo(String admId, String admdate, String admno, String bedno, String doc_hisId, String doc_name,
			String doc_payway, String hisId, String medicare, String name, String register, String state, String age,
			String sex, String isCPW) {
		super();
		this.admId = admId;
		this.admdate = admdate;
		this.admno = admno;
		this.bedno = bedno;
		this.doc_hisId = doc_hisId;
		this.doc_name = doc_name;
		this.doc_payway = doc_payway;
		this.hisId = hisId;
		this.medicare = medicare;
		this.name = name;
		this.register = register;
		this.state = state;
		this.age = age;
		this.sex = sex;
		this.isCPW = isCPW;
	}


	public String getAdmId() {
		return admId;
	}
	public void setAdmId(String admId) {
		this.admId = admId;
	}
	public String getAdmdate() {
		return admdate;
	}
	public void setAdmdate(String admdate) {
		this.admdate = admdate;
	}
	public String getAdmno() {
		return admno;
	}
	public void setAdmno(String admno) {
		this.admno = admno;
	}
	public String getBedno() {
		return bedno;
	}
	public void setBedno(String bedno) {
		this.bedno = bedno;
	}
	public String getDoc_hisId() {
		return doc_hisId;
	}
	public void setDoc_hisId(String doc_hisId) {
		this.doc_hisId = doc_hisId;
	}
	public String getDoc_name() {
		return doc_name;
	}
	public void setDoc_name(String doc_name) {
		this.doc_name = doc_name;
	}
	
	public String getDoc_payway() {
		return doc_payway;
	}
	public void setDoc_payway(String doc_payway) {
		this.doc_payway = doc_payway;
	}
	public String getHisId() {
		return hisId;
	}
	public void setHisId(String hisId) {
		this.hisId = hisId;
	}
	public String getMedicare() {
		return medicare;
	}
	public void setMedicare(String medicare) {
		this.medicare = medicare;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRegister() {
		return register;
	}
	public void setRegister(String register) {
		this.register = register;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}


	public String getIsCPW() {
		return isCPW;
	}


	public void setIsCPW(String isCPW) {
		this.isCPW = isCPW;
	}
	
	
	
}
