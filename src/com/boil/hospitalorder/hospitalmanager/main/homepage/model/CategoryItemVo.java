package com.boil.hospitalorder.hospitalmanager.main.homepage.model;

import java.io.Serializable;

public class CategoryItemVo implements Serializable{

	private static final long serialVersionUID = 1L;

	private String startDate;
	
	private String endDate;
	
	private String depTypeId;
	
	private String dataTypeId;
	
	private String admTypeId;

	public CategoryItemVo() {
		super();
	}
	
	public CategoryItemVo(String startDate, String endDate, String depTypeId,
			String dataTypeId, String admTypeId) {
		super();
		this.startDate = startDate;
		this.endDate = endDate;
		this.depTypeId = depTypeId;
		this.dataTypeId = dataTypeId;
		this.admTypeId = admTypeId;
	}

	public CategoryItemVo(String startDate, String endDate, String depTypeId,
			String admTypeId) {
		super();
		this.startDate = startDate;
		this.endDate = endDate;
		this.depTypeId = depTypeId;
		this.admTypeId = admTypeId;
	}
	
	public CategoryItemVo(String startDate, String endDate, String depTypeId) {
		super();
		this.startDate = startDate;
		this.endDate = endDate;
		this.depTypeId = depTypeId;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getDepTypeId() {
		return depTypeId;
	}

	public void setDepTypeId(String depTypeId) {
		this.depTypeId = depTypeId;
	}

	public String getDataTypeId() {
		return dataTypeId;
	}

	public void setDataTypeId(String dataTypeId) {
		this.dataTypeId = dataTypeId;
	}

	public String getAdmTypeId() {
		return admTypeId;
	}

	public void setAdmTypeId(String admTypeId) {
		this.admTypeId = admTypeId;
	}

	@Override
	public String toString() {
		return "CategoryItemVo [startDate=" + startDate + ", endDate="
				+ endDate + ", depTypeId=" + depTypeId + ", dataTypeId="
				+ dataTypeId + ", admTypeId=" + admTypeId + "]";
	}
}
