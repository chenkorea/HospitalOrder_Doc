package com.boil.hospitalorder.hospitaldoctor.main.homepage.model;

import java.io.Serializable;

public class PatientMedicalRecordVo implements Serializable{

	private static final long serialVersionUID = 1L;

	private String id;
	
	private String mdesc;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMdesc() {
		return mdesc;
	}

	public void setMdesc(String mdesc) {
		this.mdesc = mdesc;
	}
	
}
