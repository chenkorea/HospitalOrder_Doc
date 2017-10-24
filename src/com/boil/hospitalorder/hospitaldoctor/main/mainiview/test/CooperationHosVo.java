package com.boil.hospitalorder.hospitaldoctor.main.mainiview.test;

import java.io.Serializable;

public class CooperationHosVo implements Serializable{

	
	//医院地址
	private String address;
	//医院id
	private String id;
	//医院名称
	private String name;
	//医院等级
	private String qualif;
	
	
	public CooperationHosVo() {
	}
	
	public CooperationHosVo(String address, String id, String name, String qualif) {
		super();
		this.address = address;
		this.id = id;
		this.name = name;
		this.qualif = qualif;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
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
	public String getQualif() {
		return qualif;
	}
	public void setQualif(String qualif) {
		this.qualif = qualif;
	}
	
	
	
}
