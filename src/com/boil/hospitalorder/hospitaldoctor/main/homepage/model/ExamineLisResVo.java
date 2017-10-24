package com.boil.hospitalorder.hospitaldoctor.main.homepage.model;

import java.io.Serializable;

/**
 * 查询体检记录列表实体
 * 
 * @author Administrator
 *
 */
public class ExamineLisResVo implements Serializable {

	private String flag;
	private String jyResname;
	private String projectId;
	private String reference;
	private String resDhc;
	private String result;
	private String tjId;

	public ExamineLisResVo() {

	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getJyResname() {
		return jyResname;
	}

	public void setJyResname(String jyResname) {
		this.jyResname = jyResname;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public String getResDhc() {
		return resDhc;
	}

	public void setResDhc(String resDhc) {
		this.resDhc = resDhc;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getTjId() {
		return tjId;
	}

	public void setTjId(String tjId) {
		this.tjId = tjId;
	}

}
