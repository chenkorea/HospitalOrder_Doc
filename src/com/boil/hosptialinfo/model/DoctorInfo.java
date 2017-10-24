package com.boil.hosptialinfo.model;

import java.io.Serializable;

public class DoctorInfo implements Serializable {
	
	private String resultCode;
	
	private String resultContent;
	
	/**医生名称*/
	private String dcName;

	/**擅长*/
	private String specialty;
	
	/**医生简介*/
	private String remarks;
	
	/**科室名称*/
	private String ksname;
	
	private String pyCode;
	
	/**职称*/
	private String major;
	
	/**图片名字*/
	private String picture;
	
	/**医生Code*/
	private String hisid;
	
	/**科室id*/
	private String ksHisId;
	
	
	private String doc_hos;
	
	private String headImage;
	

	public DoctorInfo(String dcName, String remarks, String ksname,
			String doc_hos, String headImage) {
		super();
		this.dcName = dcName;
		this.remarks = remarks;
		this.ksname = ksname;
		this.doc_hos = doc_hos;
		this.headImage = headImage;
	}



	public String getHeadImage() {
		return headImage;
	}



	public void setHeadImage(String headImage) {
		this.headImage = headImage;
	}



	public String getResultCode() {
		return resultCode;
	}



	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}



	public String getResultContent() {
		return resultContent;
	}



	public void setResultContent(String resultContent) {
		this.resultContent = resultContent;
	}

	public String getDcName() {
		return dcName;
	}

	public void setDcName(String dcName) {
		this.dcName = dcName;
	}



	public String getSpecialty() {
		return specialty;
	}

	public void setSpecialty(String specialty) {
		this.specialty = specialty;
	}

	public String getRemarks() {
		return remarks;
	}



	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getKsname() {
		return ksname;
	}

	public void setKsname(String ksname) {
		this.ksname = ksname;
	}

	public String getPyCode() {
		return pyCode;
	}


	public void setPyCode(String pyCode) {
		this.pyCode = pyCode;
	}



	public String getMajor() {
		return major;
	}



	public void setMajor(String major) {
		this.major = major;
	}



	public String getPicture() {
		return picture;
	}



	public void setPicture(String picture) {
		this.picture = picture;
	}



	public String getHisid() {
		return hisid;
	}



	public void setHisid(String hisid) {
		this.hisid = hisid;
	}

	public String getKsHisId() {
		return ksHisId;
	}



	public void setKsHisId(String ksHisId) {
		this.ksHisId = ksHisId;
	}

	public String getDoc_hos() {
		return doc_hos;
	}



	public void setDoc_hos(String doc_hos) {
		this.doc_hos = doc_hos;
	}



	public DoctorInfo() {
		super();
	}
}
