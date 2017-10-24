package com.boil.hospitalorder.hospitaldoctor.main.homepage.model;

import java.io.Serializable;

/**
 *查询体检记录列表实体
 * @author Administrator
 *
 */
public class ExamineXdVo implements Serializable{

	
	private String tjId;
	
	private String returnDate;
	
	private String xdtRes;
	
	private String xdtDoc;
	
	public ExamineXdVo() {
		
	}

	

	public String getTjId() {
		return tjId;
	}

	public void setTjId(String tjId) {
		this.tjId = tjId;
	}

	public String getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(String returnDate) {
		this.returnDate = returnDate;
	}

	public String getXdtRes() {
		return xdtRes;
	}

	public void setXdtRes(String xdtRes) {
		this.xdtRes = xdtRes;
	}

	public String getXdtDoc() {
		return xdtDoc;
	}

	public void setXdtDoc(String xdtDoc) {
		this.xdtDoc = xdtDoc;
	}

	
}
