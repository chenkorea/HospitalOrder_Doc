package com.boil.hospitalorder.hospitaldoctor.main.homepage.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class QulityCtrVo implements Serializable{

	private static final long serialVersionUID = 1L;

	private String id;
	
	private String admno;
	
	private String name;
	
	private List<QulityCrtContVo> childVo = new ArrayList<QulityCrtContVo>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAdmno() {
		return admno;
	}

	public void setAdmno(String admno) {
		this.admno = admno;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<QulityCrtContVo> getChildVo() {
		return childVo;
	}

	public void setChildVo(List<QulityCrtContVo> childVo) {
		this.childVo = childVo;
	}
	
}
