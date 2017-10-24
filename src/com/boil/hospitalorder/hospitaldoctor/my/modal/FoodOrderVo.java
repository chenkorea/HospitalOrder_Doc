package com.boil.hospitalorder.hospitaldoctor.my.modal;

import java.io.Serializable;
import java.util.List;

/**
 * 订单实体
 * 
 * @author mengjiyong
 *
 */
public class FoodOrderVo implements Serializable{

	private String id;
	private String orderNum;
	private String hospitalId;
	private String canteenId;
	private String userId;
	private String orderStatusId;
	private String hospitalName;
	private String canteenName;
	private String orderStatus;
	private String userName;
	private String userTypeCode;
	private String userPhone;
	private String orderPrice;
	private String orderAddr;
	private String remark;
	private String createTime;
	private String updateTime;
	private String expectSendTime;
	

	private List<StaffDiningVo> cookbookCountVos;

	public FoodOrderVo() {
	}

	
	public FoodOrderVo(String id, String orderNum, String hospitalId, String canteenId, String userId,
			String orderStatusId, String hospitalName, String canteenName, String orderStatus, String userName,
			String userTypeCode, String userPhone, String orderPrice, String orderAddr, String remark,
			String createTime, String updateTime, String expectSendTime, List<StaffDiningVo> cookbookCountVos) {
		super();
		this.id = id;
		this.orderNum = orderNum;
		this.hospitalId = hospitalId;
		this.canteenId = canteenId;
		this.userId = userId;
		this.orderStatusId = orderStatusId;
		this.hospitalName = hospitalName;
		this.canteenName = canteenName;
		this.orderStatus = orderStatus;
		this.userName = userName;
		this.userTypeCode = userTypeCode;
		this.userPhone = userPhone;
		this.orderPrice = orderPrice;
		this.orderAddr = orderAddr;
		this.remark = remark;
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.expectSendTime = expectSendTime;
		this.cookbookCountVos = cookbookCountVos;
	}


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}

	public String getHospitalId() {
		return hospitalId;
	}

	public void setHospitalId(String hospitalId) {
		this.hospitalId = hospitalId;
	}

	public String getCanteenId() {
		return canteenId;
	}

	public void setCanteenId(String canteenId) {
		this.canteenId = canteenId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getOrderStatusId() {
		return orderStatusId;
	}

	public void setOrderStatusId(String orderStatusId) {
		this.orderStatusId = orderStatusId;
	}

	public String getHospitalName() {
		return hospitalName;
	}

	public void setHospitalName(String hospitalName) {
		this.hospitalName = hospitalName;
	}

	public String getCanteenName() {
		return canteenName;
	}

	public void setCanteenName(String canteenName) {
		this.canteenName = canteenName;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserTypeCode() {
		return userTypeCode;
	}

	public void setUserTypeCode(String userTypeCode) {
		this.userTypeCode = userTypeCode;
	}

	public String getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

	public String getOrderPrice() {
		return orderPrice;
	}

	public void setOrderPrice(String orderPrice) {
		this.orderPrice = orderPrice;
	}

	public String getOrderAddr() {
		return orderAddr;
	}

	public void setOrderAddr(String orderAddr) {
		this.orderAddr = orderAddr;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public List<StaffDiningVo> getCookbookCountVos() {
		return cookbookCountVos;
	}

	public void setCookbookCountVos(List<StaffDiningVo> cookbookCountVos) {
		this.cookbookCountVos = cookbookCountVos;
	}


	public String getExpectSendTime() {
		return expectSendTime;
	}


	public void setExpectSendTime(String expectSendTime) {
		this.expectSendTime = expectSendTime;
	}

	
	
}
