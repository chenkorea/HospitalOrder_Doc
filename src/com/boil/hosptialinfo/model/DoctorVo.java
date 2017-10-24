package com.boil.hosptialinfo.model;

import java.io.Serializable;

public class DoctorVo implements Serializable {

	
	private String head_img;
	
	private String doctor_id;
	
	private String doctor_name;
	
	private String good_at;
	
	private String grades;
	
	private String hospital;
	
	private String depParentName;
	
	private String depChildName;
	
	private String doctorRemark;
	
	
	private boolean is_have = false;
	

	public String getDepParentName() {
		return depParentName;
	}



	public void setDepParentName(String depParentName) {
		this.depParentName = depParentName;
	}



	public String getDepChildName() {
		return depChildName;
	}



	public void setDepChildName(String depChildName) {
		this.depChildName = depChildName;
	}
	public String getHead_img() {
		return head_img;
	}

	public void setHead_img(String head_img) {
		this.head_img = head_img;
	}

	public String getDoctor_id() {
		return doctor_id;
	}

	public void setDoctor_id(String doctor_id) {
		this.doctor_id = doctor_id;
	}

	public String getDoctor_name() {
		return doctor_name;
	}

	public void setDoctor_name(String doctor_name) {
		this.doctor_name = doctor_name;
	}

	public String getGood_at() {
		return good_at;
	}

	public void setGood_at(String good_at) {
		this.good_at = good_at;
	}

	public String getGrades() {
		return grades;
	}

	public void setGrades(String grades) {
		this.grades = grades;
	}

	public String getHospital() {
		return hospital;
	}

	public void setHospital(String hospital) {
		this.hospital = hospital;
	}

	public boolean isIs_have() {
		return is_have;
	}

	public void setIs_have(boolean is_have) {
		this.is_have = is_have;
	}
	

	public String getDoctorRemark() {
		return doctorRemark;
	}



	public void setDoctorRemark(String doctorRemark) {
		this.doctorRemark = doctorRemark;
	}



	public DoctorVo(String doctor_id, String doctor_name, String good_at,
			String grades, String hospital, boolean is_have, String head_img) {
		super();
		this.doctor_id = doctor_id;
		this.doctor_name = doctor_name;
		this.good_at = good_at;
		this.grades = grades;
		this.hospital = hospital;
		this.is_have = is_have;
		this.head_img = head_img;
	}

	public DoctorVo() {
		super();
	}
	
}
