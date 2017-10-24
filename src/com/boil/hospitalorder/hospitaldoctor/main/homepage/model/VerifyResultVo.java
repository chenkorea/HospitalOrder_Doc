package com.boil.hospitalorder.hospitaldoctor.main.homepage.model;

/**
 * 
 * 检验结果映射实体。
 * 
 * @author ChenYong
 * @date 2016-03-01
 * 
 */
public class VerifyResultVo {

	
	private String lisDate;
	private String lisId;
	private String orderId;
	private String orderName;
	
	private String resDhc;
	private String resName;
	private String resNum;
	private String resRanges;
	private String resResult;
	private String resTag;
	
	public VerifyResultVo() {
	}
	
	public String getLisDate() {
		return lisDate;
	}
	public void setLisDate(String lisDate) {
		this.lisDate = lisDate;
	}
	public String getLisId() {
		return lisId;
	}
	public void setLisId(String lisId) {
		this.lisId = lisId;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getOrderName() {
		return orderName;
	}
	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}

	public String getResDhc() {
		return resDhc;
	}

	public void setResDhc(String resDhc) {
		this.resDhc = resDhc;
	}

	public String getResName() {
		return resName;
	}

	public void setResName(String resName) {
		this.resName = resName;
	}

	public String getResNum() {
		return resNum;
	}

	public void setResNum(String resNum) {
		this.resNum = resNum;
	}

	public String getResRanges() {
		return resRanges;
	}

	public void setResRanges(String resRanges) {
		this.resRanges = resRanges;
	}

	public String getResResult() {
		return resResult;
	}

	public void setResResult(String resResult) {
		this.resResult = resResult;
	}

	public String getResTag() {
		return resTag;
	}

	public void setResTag(String resTag) {
		this.resTag = resTag;
	}
	
	
	
}