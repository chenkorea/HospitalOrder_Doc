package com.boil.hospitalorder.hospitaldoctor.main.homepage.model;

import java.io.Serializable;

/**
 *查询体检记录列表实体
 * @author Administrator
 *
 */
public class ExamineVo implements Serializable{

	
	private String tjId;
	
	private String userName;
	
	private String gender;
	
	private String ages;
	
	private String account;
	
	private String address;
	
	private String tjDate;
	
	private String cellphone;
	
	private String tjClass;

	public ExamineVo() {
	}

	public String getTjId() {
		return tjId;
	}

	public void setTjId(String tjId) {
		this.tjId = tjId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getAges() {
		return ages;
	}

	public void setAges(String ages) {
		this.ages = ages;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTjDate() {
		return tjDate;
	}

	public void setTjDate(String tjDate) {
		this.tjDate = tjDate;
	}
	

	public String getCellphone() {
		return cellphone;
	}

	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}

	public String getTjClass() {
		return tjClass;
	}

	public void setTjClass(String tjClass) {
		this.tjClass = tjClass;
	}

	
	
}
