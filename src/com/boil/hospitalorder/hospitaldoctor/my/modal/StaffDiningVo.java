package com.boil.hospitalorder.hospitaldoctor.my.modal;

import java.io.Serializable;
import java.util.List;

public class StaffDiningVo implements Serializable{
	
	private String id;
	private String webUserId ;
	private String dishName ;
	private String dishType ;
	private String effect ;
	private String taste ;
	private String cookType ;
	private String dishLogoPath ;
	private List<String> dishPicPaths ;
	private String dishPrice ;
	private String dishDesc ;
	private String remark ;
	private String createTime ;
	private String updateTime ;
	private String dishCount;
	private String makeTime;
	private String dishRemark;
	
	public StaffDiningVo() {
	}

	

	public StaffDiningVo(String id, String webUserId, String dishName, String dishType, String effect, String taste,
			String cookType, String dishLogoPath, List<String> dishPicPaths, String dishPrice, String dishDesc,
			String remark, String createTime, String updateTime, String dishCount, String makeTime, String dishRemark) {
		super();
		this.id = id;
		this.webUserId = webUserId;
		this.dishName = dishName;
		this.dishType = dishType;
		this.effect = effect;
		this.taste = taste;
		this.cookType = cookType;
		this.dishLogoPath = dishLogoPath;
		this.dishPicPaths = dishPicPaths;
		this.dishPrice = dishPrice;
		this.dishDesc = dishDesc;
		this.remark = remark;
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.dishCount = dishCount;
		this.makeTime = makeTime;
		this.dishRemark = dishRemark;
	}



	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getWebUserId() {
		return webUserId;
	}

	public void setWebUserId(String webUserId) {
		this.webUserId = webUserId;
	}

	public String getDishName() {
		return dishName;
	}

	public void setDishName(String dishName) {
		this.dishName = dishName;
	}

	public String getDishType() {
		return dishType;
	}

	public void setDishType(String dishType) {
		this.dishType = dishType;
	}

	public String getEffect() {
		return effect;
	}

	public void setEffect(String effect) {
		this.effect = effect;
	}

	public String getTaste() {
		return taste;
	}

	public void setTaste(String taste) {
		this.taste = taste;
	}

	public String getCookType() {
		return cookType;
	}

	public void setCookType(String cookType) {
		this.cookType = cookType;
	}

	public String getDishLogoPath() {
		return dishLogoPath;
	}

	public void setDishLogoPath(String dishLogoPath) {
		this.dishLogoPath = dishLogoPath;
	}

	public List<String> getDishPicPaths() {
		return dishPicPaths;
	}

	public void setDishPicPaths(List<String> dishPicPaths) {
		this.dishPicPaths = dishPicPaths;
	}

	public String getDishPrice() {
		return dishPrice;
	}

	public void setDishPrice(String dishPrice) {
		this.dishPrice = dishPrice;
	}

	public String getDishDesc() {
		return dishDesc;
	}

	public void setDishDesc(String dishDesc) {
		this.dishDesc = dishDesc;
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

	public String getDishCount() {
		return dishCount;
	}

	public void setDishCount(String dishCount) {
		this.dishCount = dishCount;
	}



	public String getMakeTime() {
		return makeTime;
	}



	public void setMakeTime(String makeTime) {
		this.makeTime = makeTime;
	}



	public String getDishRemark() {
		return dishRemark;
	}



	public void setDishRemark(String dishRemark) {
		this.dishRemark = dishRemark;
	}
	
	
}
