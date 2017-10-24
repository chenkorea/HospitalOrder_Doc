package com.boil.hospitalorder.hospitaldoctor.main.homepage.model;

/**
 * 
 * 检查结果映射实体。
 * 
 * @author ChenYong
 * @date 2016-03-01
 * 
 */
public class CheckResultVo {
	
	
	private String direction;
	private String gender;
	private String hisid;
	private String name;
	private String reportDate;
	private String reportId;
	private String reportType;
	private String resType;
	
	public CheckResultVo() {
	}
	
	public String getDirection() {
		return direction;
	}
	public void setDirection(String direction) {
		this.direction = direction;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getHisid() {
		return hisid;
	}
	public void setHisid(String hisid) {
		this.hisid = hisid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getReportDate() {
		return reportDate;
	}
	public void setReportDate(String reportDate) {
		this.reportDate = reportDate;
	}
	public String getReportId() {
		return reportId;
	}
	public void setReportId(String reportId) {
		this.reportId = reportId;
	}
	public String getReportType() {
		return reportType;
	}
	public void setReportType(String reportType) {
		this.reportType = reportType;
	}

	public String getResType() {
		return resType;
	}

	public void setResType(String resType) {
		this.resType = resType;
	}
	
	
}