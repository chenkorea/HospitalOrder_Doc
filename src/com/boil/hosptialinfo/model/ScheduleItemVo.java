package com.boil.hosptialinfo.model;

import java.io.Serializable;


public class ScheduleItemVo implements Serializable{

	private String ResultCode;
	
	private String recordCount;
	
	private String ResultContent;
	
	/**门诊排班项记录标识*/
	private String scheduleItemCode;
	
	/**门诊排班日期(YYYY-MM-DD)*/
	private String serviceDate;
	
	/**星期数(1-7)*/
	private String weekDay;
	
	/**排班时段代码*/
	private String sessionCode;
	
	/**排班时段名称*/
	private String sessionName;
	
	/**开始时间(HH:MM)*/
	private String startTime;
	
	/**结束时间(HH:MM)*/
	private String endTime;
	
	/**科室代码*/
	private String departmentCode;
	
	/**科室名称*/
	private String departmentName;
	
	/**诊室代码*/
	private String clinicRoomCode;
	
	/**诊室名称*/
	private String clinicRoomName;
	
	/**医生代码*/
	private String doctorCode;
	
	/**医生名称*/
	private String doctorName;
	
	/**医生职称代码*/
	private String doctorTitleCode;
	
	/**医生职称*/
	private String doctorTitle;
	
	/**医生专长*/
	private String doctorSpec;
	
	private String doctorType;
	
	/**出诊级别代码*/
	private String doctorSessTypeCode;
	
	/**出诊级别*/
	private String doctorSessType;
	
	/**专业代码*/
	private String serviceCode;
	
	/**专业名称*/
	private String serviceName;
	
	/**预约挂号总费用*/
	private String fee;
	
	/**挂号费*/
	private String regFee;
	
	/**诊查费*/
	private String checkupFee;
	
	/**服务费*/
	private String serviceFee;
	
	/**其他费*/
	private String otherFee;
	
	/**剩余号数量*/
	private String availableNum;
	
	/**就诊地址*/
	private String admitAddress;
	
	/**候诊时间范围*/
	private String admitTimeRange;
	
	/**备注*/
	private String note;

	public String getDoctorType() {
		return doctorType;
	}

	public void setDoctorType(String doctorType) {
		this.doctorType = doctorType;
	}

	public String getScheduleItemCode() {
		return scheduleItemCode;
	}

	public void setScheduleItemCode(String scheduleItemCode) {
		this.scheduleItemCode = scheduleItemCode;
	}

	public String getServiceDate() {
		return serviceDate;
	}

	public void setServiceDate(String serviceDate) {
		this.serviceDate = serviceDate;
	}

	public String getWeekDay() {
		return weekDay;
	}

	public void setWeekDay(String weekDay) {
		this.weekDay = weekDay;
	}

	public String getSessionCode() {
		return sessionCode;
	}

	public void setSessionCode(String sessionCode) {
		this.sessionCode = sessionCode;
	}

	public String getSessionName() {
		return sessionName;
	}

	public void setSessionName(String sessionName) {
		this.sessionName = sessionName;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getDepartmentCode() {
		return departmentCode;
	}

	public void setDepartmentCode(String departmentCode) {
		this.departmentCode = departmentCode;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getClinicRoomCode() {
		return clinicRoomCode;
	}

	public void setClinicRoomCode(String clinicRoomCode) {
		this.clinicRoomCode = clinicRoomCode;
	}

	public String getClinicRoomName() {
		return clinicRoomName;
	}

	public void setClinicRoomName(String clinicRoomName) {
		this.clinicRoomName = clinicRoomName;
	}

	public String getDoctorCode() {
		return doctorCode;
	}

	public void setDoctorCode(String doctorCode) {
		this.doctorCode = doctorCode;
	}

	public String getDoctorName() {
		return doctorName;
	}

	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}

	public String getDoctorTitleCode() {
		return doctorTitleCode;
	}

	public void setDoctorTitleCode(String doctorTitleCode) {
		this.doctorTitleCode = doctorTitleCode;
	}

	public String getDoctorTitle() {
		return doctorTitle;
	}

	public void setDoctorTitle(String doctorTitle) {
		this.doctorTitle = doctorTitle;
	}

	public String getDoctorSpec() {
		return doctorSpec;
	}

	public void setDoctorSpec(String doctorSpec) {
		this.doctorSpec = doctorSpec;
	}

	public String getDoctorSessTypeCode() {
		return doctorSessTypeCode;
	}

	public void setDoctorSessTypeCode(String doctorSessTypeCode) {
		this.doctorSessTypeCode = doctorSessTypeCode;
	}

	public String getDoctorSessType() {
		return doctorSessType;
	}

	public void setDoctorSessType(String doctorSessType) {
		this.doctorSessType = doctorSessType;
	}

	public String getServiceCode() {
		return serviceCode;
	}

	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getFee() {
		return fee;
	}

	public void setFee(String fee) {
		this.fee = fee;
	}

	public String getRegFee() {
		return regFee;
	}

	public void setRegFee(String regFee) {
		this.regFee = regFee;
	}

	public String getCheckupFee() {
		return checkupFee;
	}

	public void setCheckupFee(String checkupFee) {
		this.checkupFee = checkupFee;
	}

	public String getServiceFee() {
		return serviceFee;
	}

	public void setServiceFee(String serviceFee) {
		this.serviceFee = serviceFee;
	}

	public String getOtherFee() {
		return otherFee;
	}

	public void setOtherFee(String otherFee) {
		this.otherFee = otherFee;
	}

	public String getAvailableNum() {
		return availableNum;
	}

	public void setAvailableNum(String availableNum) {
		this.availableNum = availableNum;
	}

	public String getAdmitAddress() {
		return admitAddress;
	}

	public void setAdmitAddress(String admitAddress) {
		this.admitAddress = admitAddress;
	}

	public String getAdmitTimeRange() {
		return admitTimeRange;
	}

	public void setAdmitTimeRange(String admitTimeRange) {
		this.admitTimeRange = admitTimeRange;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getResultCode() {
		return ResultCode;
	}

	public void setResultCode(String resultCode) {
		ResultCode = resultCode;
	}

	public String getResultContent() {
		return ResultContent;
	}

	public void setResultContent(String resultContent) {
		ResultContent = resultContent;
	}

	public String getRecordCount() {
		return recordCount;
	}

	public void setRecordCount(String recordCount) {
		this.recordCount = recordCount;
	}

	@Override
	public String toString() {
		return "ScheduleItemVo [ResultCode=" + ResultCode + ", recordCount="
				+ recordCount + ", ResultContent=" + ResultContent
				+ ", scheduleItemCode=" + scheduleItemCode + ", serviceDate="
				+ serviceDate + ", weekDay=" + weekDay + ", sessionCode="
				+ sessionCode + ", sessionName=" + sessionName + ", startTime="
				+ startTime + ", endTime=" + endTime + ", departmentCode="
				+ departmentCode + ", departmentName=" + departmentName
				+ ", clinicRoomCode=" + clinicRoomCode + ", clinicRoomName="
				+ clinicRoomName + ", doctorCode=" + doctorCode
				+ ", doctorName=" + doctorName + ", doctorTitleCode="
				+ doctorTitleCode + ", doctorTitle=" + doctorTitle
				+ ", doctorSpec=" + doctorSpec + ", doctorType=" + doctorType
				+ ", doctorSessTypeCode=" + doctorSessTypeCode
				+ ", doctorSessType=" + doctorSessType + ", serviceCode="
				+ serviceCode + ", serviceName=" + serviceName + ", fee=" + fee
				+ ", regFee=" + regFee + ", checkupFee=" + checkupFee
				+ ", serviceFee=" + serviceFee + ", otherFee=" + otherFee
				+ ", availableNum=" + availableNum + ", admitAddress="
				+ admitAddress + ", admitTimeRange=" + admitTimeRange
				+ ", note=" + note + "]";
	}
}
