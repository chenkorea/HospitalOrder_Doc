package com.boil.hospitalorder.hospitaldoctor.main.homepage.model;

import java.io.Serializable;

/**
 * 医嘱实体
 * 
 * @author mengjiyong
 *
 */
public class AdviceVo implements Serializable{

	 private String orderDate;
	 private String orderDoc;
	 private String orderFreg;
	 private String orderId;
	 private String orderName;
	 private String orderSta;
	 private String orderTime;
	 private String orderType;
	
	public AdviceVo() {
	}

	public String getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	public String getOrderDoc() {
		return orderDoc;
	}

	public void setOrderDoc(String orderDoc) {
		this.orderDoc = orderDoc;
	}

	public String getOrderFreg() {
		return orderFreg;
	}

	public void setOrderFreg(String orderFreg) {
		this.orderFreg = orderFreg;
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

	public String getOrderSta() {
		return orderSta;
	}

	public void setOrderSta(String orderSta) {
		this.orderSta = orderSta;
	}

	public String getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	
	
}
