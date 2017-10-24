package com.boil.hospitalsecond.docpatientcircle.model;

/**
 * 
 * 院区映射实体类。
 * 
 * @author ChenYong
 * @date 2016-12-20
 *
 */
public class AreaVo {
	/** 院区编号 */
	private Long id;
	/** 院区名称 */
	private String areaname;
	
	/**
	 * 
	 * 默认构造器。
	 * 
	 */
	public AreaVo() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAreaname() {
		return areaname;
	}

	public void setAreaname(String areaname) {
		this.areaname = areaname;
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
		AreaVo other = (AreaVo) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return String.format("Area [id=%s, areaname=%s]", id, areaname);
	}
}