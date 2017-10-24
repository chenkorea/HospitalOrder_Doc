package com.boil.hospitalsecond.docpatientcircle.model;

import java.io.Serializable;

public class MessagePlatformDeVo implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;
	
	private String user_name;
	
	private String headUrl;
	
	private String back_time;
	
	private String back_content;

	public MessagePlatformDeVo(String id, String user_name, String headUrl,
			String back_time, String back_content) {
		super();
		this.id = id;
		this.user_name = user_name;
		this.headUrl = headUrl;
		this.back_time = back_time;
		this.back_content = back_content;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getHeadUrl() {
		return headUrl;
	}

	public void setHeadUrl(String headUrl) {
		this.headUrl = headUrl;
	}

	public String getBack_time() {
		return back_time;
	}

	public void setBack_time(String back_time) {
		this.back_time = back_time;
	}

	public String getBack_content() {
		return back_content;
	}

	public void setBack_content(String back_content) {
		this.back_content = back_content;
	}

	@Override
	public String toString() {
		return "MessagePlatformDeVo [id=" + id + ", user_name=" + user_name
				+ ", headUrl=" + headUrl + ", back_time=" + back_time
				+ ", back_content=" + back_content + "]";
	}
}
