package com.boil.hospitalsecond.docpatientcircle.model;

/**
 * 
 * 部门映射实体类。
 * 
 * @author ChenYong
 * @date 2016-12-20
 *
 */
public class DeptVo {
	/** 部门 ID */
	private Long id;
	/** 医院 ID */
	private Long hisId;
	/** 部门名称 */
	private String name;
	
	/**
	 * 
	 * 默认构造器。
	 * 
	 */
	public DeptVo() {
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
		DeptVo other = (DeptVo) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return String.format("Dept [id=%s, hisId=%s, name=%s]", id, hisId, name);
	}
}