package com.boil.hospitalorder.hospitaldoctor.my.modal;

import java.io.Serializable;

/**
 * 菜
 * 
 * @author mengjiyong
 *
 */
public class FoodVo implements Serializable {

	private String id;
	private String dictName;
	private boolean isSelect = false;

	public FoodVo() {
	}

	public FoodVo(String id, String dictName, boolean isSelect) {
		super();
		this.id = id;
		this.dictName = dictName;
		this.isSelect = isSelect;
	}

	public Boolean getIsSelect() {
		return isSelect;
	}


	public void setIsSelect(Boolean isSelect) {
		this.isSelect = isSelect;
	}


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDictName() {
		return dictName;
	}

	public void setDictName(String dictName) {
		this.dictName = dictName;
	}

	

}
