package com.boil.profile.model;

import java.io.Serializable;

public class OrderCheckVo implements Serializable {

	private static final long serialVersionUID = 1L;
	private String price;
	private String firsttime;
	private String endtime;
	private String nowdate;
	private String time;
	private String totalnum;
	private String usablenum;
	private String usdid;
	private String rsid;
	private String rsname;
	private String areaId;
	private String areaName;
	private String payway;
	
	public OrderCheckVo() {
		super();
	}
	
	public OrderCheckVo(String price, String firsttime, String endtime,
			String nowdate, String time, String totalnum, String usablenum,
			String usdid, String rsid, String rsname, String areaId,
			String areaName, String payway) {
		super();
		this.price = price;
		this.firsttime = firsttime;
		this.endtime = endtime;
		this.nowdate = nowdate;
		this.time = time;
		this.totalnum = totalnum;
		this.usablenum = usablenum;
		this.usdid = usdid;
		this.rsid = rsid;
		this.rsname = rsname;
		this.areaId = areaId;
		this.areaName = areaName;
		this.payway = payway;
	}

	public String getPayway() {
		return payway;
	}

	public void setPayway(String payway) {
		this.payway = payway;
	}

	public String getAreaId() {
		return areaId;
	}
	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getFirsttime() {
		return firsttime;
	}
	public void setFirsttime(String firsttime) {
		this.firsttime = firsttime;
	}
	public String getEndtime() {
		return endtime;
	}
	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}
	public String getNowdate() {
		return nowdate;
	}
	public void setNowdate(String nowdate) {
		this.nowdate = nowdate;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getTotalnum() {
		return totalnum;
	}
	public void setTotalnum(String totalnum) {
		this.totalnum = totalnum;
	}
	public String getUsablenum() {
		return usablenum;
	}
	public void setUsablenum(String usablenum) {
		this.usablenum = usablenum;
	}
	public String getUsdid() {
		return usdid;
	}
	public void setUsdid(String usdid) {
		this.usdid = usdid;
	}
	public String getRsid() {
		return rsid;
	}
	public void setRsid(String rsid) {
		this.rsid = rsid;
	}
	public String getRsname() {
		return rsname;
	}
	public void setRsname(String rsname) {
		this.rsname = rsname;
	}
	
}
