package com.boil.hospitalsecond.homepage.model;

import java.io.Serializable;

public class HosDoctorVo implements Serializable{
	
	private static final long serialVersionUID = 1L;
	//医生信息
	private String id;
	private String doctortype;
	private String name;
	private String picture;
	private String remarks;
	
	//医院和部门信息
	private String dep_his_id;
	private String dep_id;
	private String dep_name;
	private String hos_id;
	private String hos_name;
	private String area_id; //院区id
	private String area_name;//院区名称
	
	//挂号信息
	private String[] schdes; //排班时间
	private String specialty;//擅长
	
	//是否关注
	private String isSub;
	
	//留言-回复内容
	private String backCcontent;
	private String backId;
	private String backCreatetime;
	private String postive;
	private String payway;
	
	private boolean isIntent = false;
	private String isPostive;
	
	public HosDoctorVo() {
	}
	
	public String getBackCcontent() {
		return backCcontent;
	}

	public void setBackCcontent(String backCcontent) {
		this.backCcontent = backCcontent;
	}

	public String getBackId() {
		return backId;
	}

	public void setBackId(String backId) {
		this.backId = backId;
	}

	public String getBackCreatetime() {
		return backCreatetime;
	}

	public void setBackCreatetime(String backCreatetime) {
		this.backCreatetime = backCreatetime;
	}

	public String getPostive() {
		return postive;
	}

	public void setPostive(String postive) {
		this.postive = postive;
	}
	
	public String getPayway() {
		return payway;
	}

	public void setPayway(String payway) {
		this.payway = payway;
	}
	
	public boolean isIntent() {
		return isIntent;
	}

	public void setIntent(boolean isIntent) {
		this.isIntent = isIntent;
	}
	
	public String getIsPostive() {
		return isPostive;
	}

	public void setIsPostive(String isPostive) {
		this.isPostive = isPostive;
	}

	public HosDoctorVo(String id, String doctortype, String name, String picture, String remarks, String dep_id,
			String dep_name, String hos_id, String hos_name, String area_id, String area_name) {
		super();
		this.id = id;
		this.doctortype = doctortype;
		this.name = name;
		this.picture = picture;
		this.remarks = remarks;
		this.dep_id = dep_id;
		this.dep_name = dep_name;
		this.hos_id = hos_id;
		this.hos_name = hos_name;
		this.area_id = area_id;
		this.area_name = area_name;
	}
	
	
	public HosDoctorVo(String id, String name, String picture, String dep_id,
			String dep_name, String hos_id, String hos_name, String area_id,
			String area_name, String backCcontent, String backId,
			String backCreatetime, String postive, String payway, String doctortype, boolean isIntent, String isPostive) {
		super();
		this.id = id;
		this.name = name;
		this.picture = picture;
		this.dep_id = dep_id;
		this.dep_name = dep_name;
		this.hos_id = hos_id;
		this.hos_name = hos_name;
		this.area_id = area_id;
		this.area_name = area_name;
		this.backCcontent = backCcontent;
		this.backId = backId;
		this.backCreatetime = backCreatetime;
		this.postive = postive;
		this.payway = payway;
		this.doctortype = doctortype;
		this.isIntent = isIntent;
		this.isPostive = isPostive;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDoctortype() {
		return doctortype;
	}
	public void setDoctortype(String doctortype) {
		this.doctortype = doctortype;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getDep_id() {
		return dep_id;
	}
	public void setDep_id(String dep_id) {
		this.dep_id = dep_id;
	}
	public String getDep_name() {
		return dep_name;
	}
	public void setDep_name(String dep_name) {
		this.dep_name = dep_name;
	}
	public String getHos_id() {
		return hos_id;
	}
	public void setHos_id(String hos_id) {
		this.hos_id = hos_id;
	}
	public String getHos_name() {
		return hos_name;
	}
	public void setHos_name(String hos_name) {
		this.hos_name = hos_name;
	}
	public String getArea_id() {
		return area_id;
	}
	public void setArea_id(String area_id) {
		this.area_id = area_id;
	}
	public String getArea_name() {
		return area_name;
	}
	public void setArea_name(String area_name) {
		this.area_name = area_name;
	}


	public String[] getSchdes() {
		return schdes;
	}


	public void setSchdes(String[] schdes) {
		this.schdes = schdes;
	}


	public String getSpecialty() {
		return specialty;
	}


	public void setSpecialty(String specialty) {
		this.specialty = specialty;
	}


	public String getDep_his_id() {
		return dep_his_id;
	}


	public void setDep_his_id(String dep_his_id) {
		this.dep_his_id = dep_his_id;
	}


	public String getIsSub() {
		return isSub;
	}


	public void setIsSub(String isSub) {
		this.isSub = isSub;
	}
	
	
	
}
