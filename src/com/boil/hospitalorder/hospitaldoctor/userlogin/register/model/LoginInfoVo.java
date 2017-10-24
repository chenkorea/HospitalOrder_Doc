package com.boil.hospitalorder.hospitaldoctor.userlogin.register.model;

import java.io.Serializable;
import java.util.List;

/**
 * 登录信息
 * @author mengjiyong
 *
 */
public class LoginInfoVo implements Serializable{
	
	
	private String id;
	private String name;
	private List<LoginDeptVo> depts;
	
	
	public LoginInfoVo() {
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
	public List<LoginDeptVo> getDepts() {
		return depts;
	}
	public void setDepts(List<LoginDeptVo> depts) {
		this.depts = depts;
	}
	
}
