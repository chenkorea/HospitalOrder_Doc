package com.boil.hospitalorder.hospitaldoctor.main.homepage.viewtools;

import java.io.Serializable;

public class TypeVo implements Serializable{

	private static final long serialVersionUID = 1L;

	private int id;
	
	private String typeNo;
	
	private String typeName;
	
	public TypeVo() {
	}

	public TypeVo(int id, String typeNo, String typeName) {
		super();
		this.id = id;
		this.typeNo = typeNo;
		this.typeName = typeName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTypeNo() {
		return typeNo;
	}

	public void setTypeNo(String typeNo) {
		this.typeNo = typeNo;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	
}
