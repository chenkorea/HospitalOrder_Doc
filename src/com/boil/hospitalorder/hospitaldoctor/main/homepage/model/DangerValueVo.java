package com.boil.hospitalorder.hospitaldoctor.main.homepage.model;

import java.io.Serializable;

public class DangerValueVo implements Serializable{

	private static final long serialVersionUID = 1L;

	private String id;
	
	private String admno;
	/**获取数据时间*/
	private String datetime;
	
	private String docId;
	
	private String docName;
	/**标签号*/
	private String labno;
	/**医嘱号*/
	private String oeordno;
	
	private String patId;
	
	private String patName;
	/**危急值内容*/
	private String resnewstr;
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
	public String getDatetime() {
		return datetime;
	}
	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}
	public String getDocId() {
		return docId;
	}
	public void setDocId(String docId) {
		this.docId = docId;
	}
	public String getDocName() {
		return docName;
	}
	public void setDocName(String docName) {
		this.docName = docName;
	}
	public String getLabno() {
		return labno;
	}
	public void setLabno(String labno) {
		this.labno = labno;
	}
	public String getOeordno() {
		return oeordno;
	}
	public void setOeordno(String oeordno) {
		this.oeordno = oeordno;
	}
	public String getPatId() {
		return patId;
	}
	public void setPatId(String patId) {
		this.patId = patId;
	}
	public String getPatName() {
		return patName;
	}
	public void setPatName(String patName) {
		this.patName = patName;
	}
	public String getResnewstr() {
		return resnewstr;
	}
	public void setResnewstr(String resnewstr) {
		this.resnewstr = resnewstr;
	}
	
}
