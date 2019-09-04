package com.sicau.forum.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class Topic {
	private Long id;
	private String category;
	private String code;
	private String content;
	private Date createdDate;
	private String title;
	private Integer idUser;
	private Boolean checked;
	private Integer upthumb;
	private Integer visit;
	
	private User user;

	public Boolean getChecked() {
		return checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}

	public Integer getUpthumb() {
		return upthumb;
	}

	public void setUpthumb(Integer upthumb) {
		this.upthumb = upthumb;
	}

	public Integer getVisit() {
		return visit;
	}

	public void setVisit(Integer visit) {
		this.visit = visit;
	}

	private List<Answer> answers;
	
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
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
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

	public List<Answer> getAnswers() {
		return answers;
	}

	public void setAnswers(List<Answer> answers) {
		this.answers = answers;
	}

	public String displayParsedCreatedDate() {
		SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd HH:mm");
		return formatter.format(this.createdDate);
    }
}
