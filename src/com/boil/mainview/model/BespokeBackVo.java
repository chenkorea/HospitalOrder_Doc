package com.boil.mainview.model;

import java.io.Serializable;

public class BespokeBackVo implements Serializable{

	private static final long serialVersionUID = 1L;
	private String id; 
	//预约单号
	private String orderCode;
	//预约序号
	private String orderNumber;
	//预约费用
	private String orderFee;
	//
	private String timeZone;
	
	private String admitAddress;
	
	private String orderCodeContent;
	
	private String batch;
	
	public BespokeBackVo(String orderCode, String orderNumber, String orderFee,
			String timeZone, String admitAddress, String orderCodeContent,
			String batch) {
		super();
		this.orderCode = orderCode;
		this.orderNumber = orderNumber;
		this.orderFee = orderFee;
		this.timeZone = timeZone;
		this.admitAddress = admitAddress;
		this.orderCodeContent = orderCodeContent;
		this.batch = batch;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getOrderFee() {
		return orderFee;
	}

	public void setOrderFee(String orderFee) {
		this.orderFee = orderFee;
	}

	public String getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}

	public String getAdmitAddress() {
		return admitAddress;
	}

	public void setAdmitAddress(String admitAddress) {
		this.admitAddress = admitAddress;
	}

	public String getOrderCodeContent() {
		return orderCodeContent;
	}

	public void setOrderCodeContent(String orderCodeContent) {
		this.orderCodeContent = orderCodeContent;
	}

	public String getBatch() {
		return batch;
	}

	public void setBatch(String batch) {
		this.batch = batch;
	}
}
