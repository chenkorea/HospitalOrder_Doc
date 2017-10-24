package com.boil.hospitalorder.hospitaldoctor.userlogin.register.model;

import java.io.Serializable;

/**
 * 
 * 常用联系人映射实体。
 * 
 * @author ChenYong
 * @date 2016-02-26
 * 
 */
public class ContactsVo implements Serializable {
	/** 序列化 ID */
	private static final long serialVersionUID = -4651489624415450098L;
	/** 编号 */
	private Integer id;
	/** 注册用户 ID */
	private Integer userId;
	/** 身份证号 */
	private String idNumber;
	/** 姓名 */
	private String userName;
	/** 民族 */
	private String national;
	/** 性别 */
	private String gender;
	/** 手机号 */
	private String cellphone;
	/** 住址 */
	private String address;
	/** 关系 */
	private String relation;
	/** 就诊卡 */
	private String cardId;
	private String prtNum;
	/** 状态 */
	private Integer status;
	/** 归档时间 */
	private String filingTime;
	/** 删除时间 */
	private String removeTime;
	/** 结果代码 */
	private Integer resultCode;
	/** 结果内容 */
	private String resultContent;
	
	/**
	 * 
	 * 默认构造器。
	 * 
	 */
	public ContactsVo() {
		
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getNational() {
		return national;
	}

	public void setNational(String national) {
		this.national = national;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getCellphone() {
		return cellphone;
	}

	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getRelation() {
		return relation;
	}

	public void setRelation(String relation) {
		this.relation = relation;
	}

	public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	public String getPrtNum() {
		return prtNum;
	}

	public void setPrtNum(String prtNum) {
		this.prtNum = prtNum;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getFilingTime() {
		return filingTime;
	}

	public void setFilingTime(String filingTime) {
		this.filingTime = filingTime;
	}

	public String getRemoveTime() {
		return removeTime;
	}

	public void setRemoveTime(String removeTime) {
		this.removeTime = removeTime;
	}

	public Integer getResultCode() {
		return resultCode;
	}

	public void setResultCode(Integer resultCode) {
		this.resultCode = resultCode;
	}

	public String getResultContent() {
		return resultContent;
	}

	public void setResultContent(String resultContent) {
		this.resultContent = resultContent;
	}

	@Override
	public String toString() {
		return "ContactsVo [id=" + id + ", userId=" + userId + ", idNumber="
				+ idNumber + ", userName=" + userName + ", national="
				+ national + ", gender=" + gender + ", cellphone=" + cellphone
				+ ", address=" + address + ", relation=" + relation
				+ ", cardId=" + cardId + ", prtNum=" + prtNum + ", status="
				+ status + ", filingTime=" + filingTime + ", removeTime="
				+ removeTime + ", resultCode=" + resultCode
				+ ", resultContent=" + resultContent + "]";
	}
}