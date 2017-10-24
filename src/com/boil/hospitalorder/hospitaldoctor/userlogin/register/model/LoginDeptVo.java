package com.boil.hospitalorder.hospitaldoctor.userlogin.register.model;

import java.io.Serializable;

public class LoginDeptVo implements Serializable{

	 private String hisId;
	 private String isdefault;
	 private String name;
	 private String supervise;
	 
	 
	 public LoginDeptVo() {
	}
	 
	 
	 
	 
	public LoginDeptVo(String hisId, String isdefault, String name, String supervise) {
		super();
		this.hisId = hisId;
		this.isdefault = isdefault;
		this.name = name;
		this.supervise = supervise;
	}




	public String getHisId() {
		return hisId;
	}
	public void setHisId(String hisId) {
		this.hisId = hisId;
	}
	public String getIsdefault() {
		return isdefault;
	}
	public void setIsdefault(String isdefault) {
		this.isdefault = isdefault;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSupervise() {
		return supervise;
	}
	public void setSupervise(String supervise) {
		this.supervise = supervise;
	}
	 
	 
	 
	 
}
