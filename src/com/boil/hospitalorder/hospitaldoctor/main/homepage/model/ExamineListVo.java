package com.boil.hospitalorder.hospitaldoctor.main.homepage.model;

import java.io.Serializable;

/**
 * 查询体检记录列表实体
 * 
 * @author Administrator
 *
 */
public class ExamineListVo implements Serializable {

	private String jyDoc;
	private String projectId;
	private String projectName;
	private String tjId;

	public ExamineListVo() {

	}
	

	public ExamineListVo(String jyDoc, String projectId, String projectName, String tjId) {
		this.jyDoc = jyDoc;
		this.projectId = projectId;
		this.projectName = projectName;
		this.tjId = tjId;
	}



	public String getJyDoc() {
		return jyDoc;
	}

	public void setJyDoc(String jyDoc) {
		this.jyDoc = jyDoc;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getTjId() {
		return tjId;
	}

	public void setTjId(String tjId) {
		this.tjId = tjId;
	}
	
	

}
