package com.boil.hospitalorder.hospitaldoctor.main.homepage.model;

import java.io.Serializable;

public class PatientCaseRecordChildVo implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private String craeteDate;
	
	private String craetePerson;
	
	private String craeteTime;
	
	private String id;
	
	private String state;
	
	private String title;
	
	private String updateDate;
	
	private String updatePerson;
	
	private String updateTime;

	public String getCraeteDate() {
		return craeteDate;
	}

	public void setCraeteDate(String craeteDate) {
		this.craeteDate = craeteDate;
	}

	public String getCraetePerson() {
		return craetePerson;
	}

	public void setCraetePerson(String craetePerson) {
		this.craetePerson = craetePerson;
	}

	public String getCraeteTime() {
		return craeteTime;
	}

	public void setCraeteTime(String craeteTime) {
		this.craeteTime = craeteTime;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	public String getUpdatePerson() {
		return updatePerson;
	}

	public void setUpdatePerson(String updatePerson) {
		this.updatePerson = updatePerson;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	
}
