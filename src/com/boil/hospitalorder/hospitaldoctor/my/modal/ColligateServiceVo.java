package com.boil.hospitalorder.hospitaldoctor.my.modal;

import java.io.Serializable;

public class ColligateServiceVo implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;
	
	private String service_name;
	
	private String service_type_id;
	
	private String service_type_name;

	public ColligateServiceVo(String id, String service_name) {
		super();
		this.id = id;
		this.service_name = service_name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getService_name() {
		return service_name;
	}

	public void setService_name(String service_name) {
		this.service_name = service_name;
	}

	public String getService_type_id() {
		return service_type_id;
	}

	public void setService_type_id(String service_type_id) {
		this.service_type_id = service_type_id;
	}

	public String getService_type_name() {
		return service_type_name;
	}

	public void setService_type_name(String service_type_name) {
		this.service_type_name = service_type_name;
	}
	
}
