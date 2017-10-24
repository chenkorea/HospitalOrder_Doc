package com.boil.hospitalorder.hospitaldoctor.main.homepage.model;

import java.io.Serializable;

/**
 *查询体检记录列表实体
 * @author Administrator
 *
 */
public class ExamineCsVo implements Serializable{

	
	private String tjId;
	
	private String returnDate;
	
	private String csCode;
	
	/**结论医生*/
	private String yxDoc;
	
	/**项目详情*/
	private String yxRes;
	
	public ExamineCsVo() {
		
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

	public String getCsCode() {
		return csCode;
	}

	public void setCsCode(String csCode) {
		this.csCode = csCode;
	}


	public String getYxDoc() {
		return yxDoc;
	}

	public void setYxDoc(String yxDoc) {
		this.yxDoc = yxDoc;
	}

	public String getYxRes() {
		return yxRes;
	}

	public void setYxRes(String yxRes) {
		this.yxRes = yxRes;
	}

	
}
