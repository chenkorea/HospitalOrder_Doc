package com.boil.hospitalsecond.docpatientcircle.model;

/**
 * 留言分类实体
 * @author mengjiyong
 *
 */
public class MessageTypeVo {

	private String id;
	private String name;
	
	
	public MessageTypeVo() {
	}
	
	public MessageTypeVo(String id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
}
