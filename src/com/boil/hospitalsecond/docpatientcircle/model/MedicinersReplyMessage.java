package com.boil.hospitalsecond.docpatientcircle.model;

/**
 * 
 * 医生回复留言实体类。
 * 
 * @author ChenYong
 * @date 2016-12-20
 *
 */
public class MedicinersReplyMessage {
	/** 回复 ID */
	private Long id;
	/** 回复类容 */
	private String content;
	/** 回复时间 */
	private String createtime;
	/** 点赞次数 */
	private Integer postive;
	/** 是否点赞过：0-未点赞，1-已点赞 */
	private Integer isPostive;
	/** 医生信息 */
	private Mediciners mediciners;
	
	/**
	 * 
	 * 默认构造器。
	 * 
	 */
	public MedicinersReplyMessage() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCreatetime() {
		return createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	public Integer getPostive() {
		return postive;
	}

	public void setPostive(Integer postive) {
		this.postive = postive;
	}

	public Integer getIsPostive() {
		return isPostive;
	}

	public void setIsPostive(Integer isPostive) {
		this.isPostive = isPostive;
	}

	public Mediciners getMediciners() {
		return mediciners;
	}

	public void setMediciners(Mediciners mediciners) {
		this.mediciners = mediciners;
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
		MedicinersReplyMessage other = (MedicinersReplyMessage) obj;
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
				"MedicinersReplyMessage [id=%s, content=%s, createtime=%s, postive=%s, isPostive=%s, mediciners=%s]",
				id, content, createtime, postive, isPostive, mediciners);
	}
}