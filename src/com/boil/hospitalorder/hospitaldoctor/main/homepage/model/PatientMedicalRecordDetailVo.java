package com.boil.hospitalorder.hospitaldoctor.main.homepage.model;

import java.io.Serializable;

public class PatientMedicalRecordDetailVo implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;
	
	private String tdesc;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTdesc() {
		return tdesc;
	}

	public void setTdesc(String tdesc) {
		this.tdesc = tdesc;
	}
}
