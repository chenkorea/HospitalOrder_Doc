package com.boil.hospitalorder.hospitaldoctor.main.homepage.model;

import java.io.Serializable;

public class PatientMessageTipVo implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String id;
	
	private String patientName;
	
	private String tipDate;
	
	private String totalStr;

	public PatientMessageTipVo() {
		super();
	}
	
	public PatientMessageTipVo(String id, String totalStr) {
		super();
		this.id = id;
		this.totalStr = totalStr;
	}



	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTotalStr() {
		return totalStr;
	}

	public void setTotalStr(String totalStr) {
		this.totalStr = totalStr;
	}

	public String getPatientName() {
		return patientName;
	}

	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}

	public String getTipDate() {
		return tipDate;
	}

	public void setTipDate(String tipDate) {
		this.tipDate = tipDate;
	}
	
}
