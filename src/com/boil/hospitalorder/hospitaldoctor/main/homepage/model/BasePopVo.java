package com.boil.hospitalorder.hospitaldoctor.main.homepage.model;

import java.io.Serializable;

public class BasePopVo implements Serializable{

	private static final long serialVersionUID = 1L;

	private String id;
	
	private String name;

	public BasePopVo(String id, String name) {
		super();
		this.id = id;
		this.name = name;
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
	
}
