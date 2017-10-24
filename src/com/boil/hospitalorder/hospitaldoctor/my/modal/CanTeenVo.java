package com.boil.hospitalorder.hospitaldoctor.my.modal;

import java.io.Serializable;

/**
 * 餐厅实体
 * 
 * @author mengjiyong
 *
 */
public class CanTeenVo implements Serializable {

	private String id;
	private String canteenName;
	private String canteenLogoId;
	private String canteenLogoPath;
	private String createTime;
	private String updateTime;

	public CanTeenVo() {
	}

	public CanTeenVo(String id, String canteenName, String canteenLogoId, String canteenLogoPath, String createTime,
			String updateTime) {
		super();
		this.id = id;
		this.canteenName = canteenName;
		this.canteenLogoId = canteenLogoId;
		this.canteenLogoPath = canteenLogoPath;
		this.createTime = createTime;
		this.updateTime = updateTime;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCanteenName() {
		return canteenName;
	}

	public void setCanteenName(String canteenName) {
		this.canteenName = canteenName;
	}

	public String getCanteenLogoId() {
		return canteenLogoId;
	}

	public void setCanteenLogoId(String canteenLogoId) {
		this.canteenLogoId = canteenLogoId;
	}

	public String getCanteenLogoPath() {
		return canteenLogoPath;
	}

	public void setCanteenLogoPath(String canteenLogoPath) {
		this.canteenLogoPath = canteenLogoPath;
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

}
