package com.boil.profile.model;

import java.io.Serializable;

/**
 * 
 * 预约检查映射实体。
 * 
 * @author ChenYong
 * @date 2016-05-03
 * 
 */
public class PacsOrderInfo2Vo implements Serializable {
	/** 序列化 ID */
	private static final long serialVersionUID = -5470465388713522738L;
	/** 序号 */
	private Integer serialCode;
	/** 医院代码：3-本院；4-二院 */
	private Integer hsId;
	/** 排班记录ID */
	private Integer usdID;
	/** 科室名称 */
	private String departName;
	/** 身份证号 */
	private String idNum;
	/** 患者姓名 */
	private String userName;
	/** 预约 ID */
	private String orderCode;
	/** 预约日期 */
	private String orderDate;
	/** 订单状态 */
	private Integer orderStatus;
	/** 订单状态说明 */
	private String orderStatusContent;
	/** 预约状态 */
	private Integer status;
	/** 电话号码 */
	private String phone;
	/** 价格 */
	private String price;
	/** 银行是否退费状态 */
	private String returnBank;
	/** his 是否可退费 */
	private String returnHis;
	/** 预约资源名称 */
	private String rsName;
	/** 检查大类 */
	private String type;
	/** 是否取票 */
	private Integer visited;
	/** 就诊日期 */
	private String visitedDate;
	/** 就诊时间段 */
	private String visitedTime;
	/** 结果代码 */
	private Integer resultCode;
	/** 结果描述 */
	private String resultCotent;

	/**
	 * 
	 * 默认构造器。
	 * 
	 */
	public PacsOrderInfo2Vo() {
		super();
	}

	public Integer getSerialCode() {
		return serialCode;
	}

	public void setSerialCode(Integer serialCode) {
		this.serialCode = serialCode;
	}

	public Integer getHsId() {
		return hsId;
	}

	public void setHsId(Integer hsId) {
		this.hsId = hsId;
	}

	public Integer getUsdID() {
		return usdID;
	}

	public void setUsdID(Integer usdID) {
		this.usdID = usdID;
	}

	public String getDepartName() {
		return departName;
	}

	public void setDepartName(String departName) {
		this.departName = departName;
	}

	public String getIdNum() {
		return idNum;
	}

	public void setIdNum(String idNum) {
		this.idNum = idNum;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public String getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	public Integer getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getOrderStatusContent() {
		return orderStatusContent;
	}

	public void setOrderStatusContent(String orderStatusContent) {
		this.orderStatusContent = orderStatusContent;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getReturnBank() {
		return returnBank;
	}

	public void setReturnBank(String returnBank) {
		this.returnBank = returnBank;
	}

	public String getReturnHis() {
		return returnHis;
	}

	public void setReturnHis(String returnHis) {
		this.returnHis = returnHis;
	}

	public String getRsName() {
		return rsName;
	}

	public void setRsName(String rsName) {
		this.rsName = rsName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getVisited() {
		return visited;
	}

	public void setVisited(Integer visited) {
		this.visited = visited;
	}

	public String getVisitedDate() {
		return visitedDate;
	}

	public void setVisitedDate(String visitedDate) {
		this.visitedDate = visitedDate;
	}

	public String getVisitedTime() {
		return visitedTime;
	}

	public void setVisitedTime(String visitedTime) {
		this.visitedTime = visitedTime;
	}

	public Integer getResultCode() {
		return resultCode;
	}

	public void setResultCode(Integer resultCode) {
		this.resultCode = resultCode;
	}

	public String getResultCotent() {
		return resultCotent;
	}

	public void setResultCotent(String resultCotent) {
		this.resultCotent = resultCotent;
	}

	@Override
	public String toString() {
		return "PacsOrderInfo2Vo [serialCode=" + serialCode + ", hsId=" + hsId
				+ ", usdID=" + usdID + ", departName=" + departName
				+ ", idNum=" + idNum + ", userName=" + userName
				+ ", orderCode=" + orderCode + ", orderDate=" + orderDate
				+ ", orderStatus=" + orderStatus + ", orderStatusContent="
				+ orderStatusContent + ", status=" + status + ", phone="
				+ phone + ", price=" + price + ", returnBank=" + returnBank
				+ ", returnHis=" + returnHis + ", rsName=" + rsName + ", type="
				+ type + ", visited=" + visited + ", visitedDate="
				+ visitedDate + ", visitedTime=" + visitedTime
				+ ", resultCode=" + resultCode + ", resultCotent="
				+ resultCotent + "]";
	}
}