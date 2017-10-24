package com.boil.hospitalorder.hospitaldoctor.userlogin.register.model;

/**
 * 
 * 注册用户映射实体。
 * 
 * @author ChenYong
 * @date 2016-02-22
 * 
 */
public class UserVo {
	
	private String dep_hisId;
	private String dep_id;
	private String dep_isdefault;
	private String dep_name;
	private String hisId;
	private String hospitals_id;
	private String hospitals_name;
	private String id;
	private String idcard;
	private String name;
	private String phone;
	private String station;
	
	public UserVo(String dep_hisId, String dep_id, String dep_isdefault, String dep_name, String hisId,
			String hospitals_id, String hospitals_name, String id, String idcard, String name, String phone,
			String station) {
		super();
		this.dep_hisId = dep_hisId;
		this.dep_id = dep_id;
		this.dep_isdefault = dep_isdefault;
		this.dep_name = dep_name;
		this.hisId = hisId;
		this.hospitals_id = hospitals_id;
		this.hospitals_name = hospitals_name;
		this.id = id;
		this.idcard = idcard;
		this.name = name;
		this.phone = phone;
		this.station = station;
	}
	public String getDep_hisId() {
		return dep_hisId;
	}
	public void setDep_hisId(String dep_hisId) {
		this.dep_hisId = dep_hisId;
	}
	public String getDep_id() {
		return dep_id;
	}
	public void setDep_id(String dep_id) {
		this.dep_id = dep_id;
	}
	public String getDep_isdefault() {
		return dep_isdefault;
	}
	public void setDep_isdefault(String dep_isdefault) {
		this.dep_isdefault = dep_isdefault;
	}
	public String getDep_name() {
		return dep_name;
	}
	public void setDep_name(String dep_name) {
		this.dep_name = dep_name;
	}
	public String getHisId() {
		return hisId;
	}
	public void setHisId(String hisId) {
		this.hisId = hisId;
	}
	public String getHospitals_id() {
		return hospitals_id;
	}
	public void setHospitals_id(String hospitals_id) {
		this.hospitals_id = hospitals_id;
	}
	public String getHospitals_name() {
		return hospitals_name;
	}
	public void setHospitals_name(String hospitals_name) {
		this.hospitals_name = hospitals_name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getIdcard() {
		return idcard;
	}
	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getStation() {
		return station;
	}
	public void setStation(String station) {
		this.station = station;
	}
	
	
}