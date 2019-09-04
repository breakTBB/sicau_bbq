package com.sicau.forum.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

public class Answer {
	private Long id;
	private Integer pid;
	private String content;
	private Date createdDate;
	private boolean useful;
	private String code;
	private Integer idTopic;
	private Integer idUser;
	private User user;
	private Topic topic;

	public Integer getIdTopic() {
		return idTopic;
	}

	public void setIdTopic(Integer idTopic) {
		this.idTopic = idTopic;
	}

	public Integer getIdUser() {
		return idUser;
	}

	public void setIdUser(Integer idUser) {
		this.idUser = idUser;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public boolean isUseful() { return useful; }

	public void setUseful(boolean useful) { this.useful = useful; }

	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Topic getTopic() {
		return topic;
	}

	public void setTopic(Topic topic) {
		this.topic = topic;
	}
	
	public String displayParsedCreatedDate() {
		SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd HH:mm");
		return formatter.format(this.createdDate);
    }

    public String displayBeginning() {
        return (this.content.length() < 32) ? this.content.concat("...") : this.content.substring(0, 30).concat("...");
    }
}
