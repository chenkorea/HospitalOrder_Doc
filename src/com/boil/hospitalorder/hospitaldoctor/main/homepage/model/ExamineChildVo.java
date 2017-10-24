package com.boil.hospitalorder.hospitaldoctor.main.homepage.model;

import java.io.Serializable;

/**
 *查询体检记录列表实体
 * @author Administrator
 *
 */
public class ExamineChildVo extends ExamineVo implements Serializable{

	
	private String tjId;
	/**项目名称*/
	private String projectName;
	
	/**体检结论*/
	private String tjRes;
	
	/**日期*/
	private String returnDate;

	/**结论医生*/
	private String mediciner;
	
	/**健康建议*/
	private String tjSuggest;
	
	
	

	public String getTjId() {
		return tjId;
	}

	public void setTjId(String tjId) {
		this.tjId = tjId;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getTjRes() {
		return tjRes;
	}

	public void setTjRes(String tjRes) {
		this.tjRes = tjRes;
	}

	public String getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(String returnDate) {
		this.returnDate = returnDate;
	}

	public String getMediciner() {
		return mediciner;
	}

	public void setMediciner(String mediciner) {
		this.mediciner = mediciner;
	}

	public String getTjSuggest() {
		return tjSuggest;
	}

	public void setTjSuggest(String tjSuggest) {
		this.tjSuggest = tjSuggest;
	}

	
	
}
