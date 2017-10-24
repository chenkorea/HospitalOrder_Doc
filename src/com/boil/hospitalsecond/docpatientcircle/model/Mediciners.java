package com.boil.hospitalsecond.docpatientcircle.model;

/**
 * 
 * 医生实体类。 
 * 
 * @author ChenYong
 * @date 2016-12-20
 *
 */
public class Mediciners {
	/** 医生 ID */
	private Long id;
	/** His系统 ID */
	private Long hisId;
	/** 医生姓名 */
	private String name;
	/** 医生类型 */
	private String doctortype;
	/** 支付方式 */
	private Integer payway;
	/** 医生头像 */
	private String picture;
	/** 所属医院 */
	private HospitalsVo hospitalsVo;
	/** 所属院区 */
	private AreaVo areaVo;
	/** 所属部门 */
	private DeptVo deptVo;
	
	/**
	 * 
	 * 默认构造器。
	 * 
	 */
	public Mediciners() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getHisId() {
		return hisId;
	}

	public void setHisId(Long hisId) {
		this.hisId = hisId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDoctortype() {
		return doctortype;
	}

	public void setDoctortype(String doctortype) {
		this.doctortype = doctortype;
	}

	public Integer getPayway() {
		return payway;
	}

	public void setPayway(Integer payway) {
		this.payway = payway;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public HospitalsVo getHospitalsVo() {
		return hospitalsVo;
	}

	public void setHospitalsVo(HospitalsVo hospitalsVo) {
		this.hospitalsVo = hospitalsVo;
	}

	public AreaVo getAreaVo() {
		return areaVo;
	}

	public void setAreaVo(AreaVo areaVo) {
		this.areaVo = areaVo;
	}

	public DeptVo getDeptVo() {
		return deptVo;
	}

	public void setDeptVo(DeptVo deptVo) {
		this.deptVo = deptVo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Mediciners other = (Mediciners) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return String.format(
				"Mediciners [id=%s, hisId=%s, name=%s, doctortype=%s, payway=%s, picture=%s, hospitalsVo=%s, areaVo=%s, deptVo=%s]",
				id, hisId, name, doctortype, payway, picture, hospitalsVo, areaVo, deptVo);
	}
}