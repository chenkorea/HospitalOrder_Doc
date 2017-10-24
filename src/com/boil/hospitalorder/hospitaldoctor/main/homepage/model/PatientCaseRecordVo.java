package com.boil.hospitalorder.hospitaldoctor.main.homepage.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PatientCaseRecordVo implements Serializable{

	private static final long serialVersionUID = 1L;

	private String id;
	
	private String haveChild;
	
	private String tdesc;

	private List<PatientCaseRecordChildVo> childVos = new ArrayList<PatientCaseRecordChildVo>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getHaveChild() {
		return haveChild;
	}

	public void setHaveChild(String haveChild) {
		this.haveChild = haveChild;
	}

	public String getTdesc() {
		return tdesc;
	}

	public void setTdesc(String tdesc) {
		this.tdesc = tdesc;
	}

	public List<PatientCaseRecordChildVo> getChildVos() {
		return childVos;
	}

	public void setChildVos(List<PatientCaseRecordChildVo> childVos) {
		this.childVos = childVos;
	}
	
}
