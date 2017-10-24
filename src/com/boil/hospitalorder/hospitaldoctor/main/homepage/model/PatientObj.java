package com.boil.hospitalorder.hospitaldoctor.main.homepage.model;

import java.io.Serializable;

public class PatientObj implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;
	
	private String name;
	
	private String treatNuber;
	
	public PatientObj(String id, String name, String treatNuber) {
		super();
		this.id = id;
		this.name = name;
		this.treatNuber = treatNuber;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTreatNuber() {
		return treatNuber;
	}

	public void setTreatNuber(String treatNuber) {
		this.treatNuber = treatNuber;
	}
	
}
