package com.boil.hospitalorder.hospitaldoctor.main.homepage.model;

import java.io.Serializable;
import java.util.List;

public class PatientBaseInfoVo implements Serializable{

	private String admId;
	private String admdate;
	private String admno;
	private String age;
	private String bedno;
	private String cellphone;
	private String contact;
	private List<CheckMessageVo> diagnos;
	private String hisId;
	private String idnumber;
	private String insurancetype;
	private String medicare;
	private String name;
	private String register;
	private String sex;
	private String state;
	
	public PatientBaseInfoVo() {
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

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getBedno() {
		return bedno;
	}

	public void setBedno(String bedno) {
		this.bedno = bedno;
	}

	public String getCellphone() {
		return cellphone;
	}

	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public List<CheckMessageVo> getDiagnos() {
		return diagnos;
	}

	public void setDiagnos(List<CheckMessageVo> diagnos) {
		this.diagnos = diagnos;
	}

	public String getHisId() {
		return hisId;
	}

	public void setHisId(String hisId) {
		this.hisId = hisId;
	}

	public String getIdnumber() {
		return idnumber;
	}

	public void setIdnumber(String idnumber) {
		this.idnumber = idnumber;
	}

	public String getInsurancetype() {
		return insurancetype;
	}

	public void setInsurancetype(String insurancetype) {
		this.insurancetype = insurancetype;
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

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	
}
