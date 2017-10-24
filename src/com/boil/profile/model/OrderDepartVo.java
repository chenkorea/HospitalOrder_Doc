package com.boil.profile.model;

import java.io.Serializable;

public class OrderDepartVo implements Serializable{

	
	private String depart_id;
	private String depart_name;
	private String depart_child_id;
	private String depart_child_name;
	
	//预约检查科id
	private String check_depart_id;
	//预约检查科名称
	private String check_depart_name;
	private boolean isPress;
	
	public OrderDepartVo() {
	}

	public OrderDepartVo(String depart_id, String depart_name, String depart_child_id, String depart_child_name,
			boolean isPress) {
		super();
		this.depart_id = depart_id;
		this.depart_name = depart_name;
		this.depart_child_id = depart_child_id;
		this.depart_child_name = depart_child_name;
		this.isPress = isPress;
	}

	public String getDepart_id() {
		return depart_id;
	}

	public void setDepart_id(String depart_id) {
		this.depart_id = depart_id;
	}

	public String getDepart_name() {
		return depart_name;
	}

	public void setDepart_name(String depart_name) {
		this.depart_name = depart_name;
	}

	public String getDepart_child_id() {
		return depart_child_id;
	}

	public void setDepart_child_id(String depart_child_id) {
		this.depart_child_id = depart_child_id;
	}

	public String getDepart_child_name() {
		return depart_child_name;
	}

	public void setDepart_child_name(String depart_child_name) {
		this.depart_child_name = depart_child_name;
	}

	public boolean isPress() {
		return isPress;
	}

	public void setPress(boolean isPress) {
		this.isPress = isPress;
	}

	public String getCheck_depart_id() {
		return check_depart_id;
	}

	public void setCheck_depart_id(String check_depart_id) {
		this.check_depart_id = check_depart_id;
	}

	public String getCheck_depart_name() {
		return check_depart_name;
	}

	public void setCheck_depart_name(String check_depart_name) {
		this.check_depart_name = check_depart_name;
	}


	
}
