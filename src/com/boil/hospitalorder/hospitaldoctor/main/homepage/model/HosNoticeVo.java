package com.boil.hospitalorder.hospitaldoctor.main.homepage.model;

import java.io.Serializable;

public class HosNoticeVo implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/*
	
	private String id;
	
	private String title;
	
	private String content;

	public HosNoticeVo() {
		super();
	}

	public HosNoticeVo(String id, String title, String content) {
		super();
		this.id = id;
		this.title = title;
		this.content = content;
	}



	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}*/
	

	private String author;
	private String id;
	private String recordtime;
	private String source;
	private String title;
	
	
	public HosNoticeVo() {
	}
	
	public HosNoticeVo(String author, String id, String recordtime, String source, String title) {
		super();
		this.author = author;
		this.id = id;
		this.recordtime = recordtime;
		this.source = source;
		this.title = title;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getRecordtime() {
		return recordtime;
	}
	public void setRecordtime(String recordtime) {
		this.recordtime = recordtime;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	

}
