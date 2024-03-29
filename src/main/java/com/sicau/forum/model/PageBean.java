package com.sicau.forum.model;

import java.util.List;

public class PageBean<T> {

	private Integer currentPage = 1;
	private Integer pageSize = 10;
	private Integer totalNum;
	private Boolean isMore;
	private Integer totalPage;
	private Integer startIndex;
	private List<T> items;

	public PageBean() {
		super();
	}

	public PageBean(Integer currentPage, Integer pageSize, Integer totalNum) {
		super();
		this.currentPage = currentPage;
		this.pageSize = pageSize;
		this.totalNum = totalNum;
		this.totalPage = (this.totalNum + this.pageSize - 1) / this.pageSize;
		this.startIndex = (this.currentPage - 1) * this.pageSize;
		this.isMore = this.currentPage < this.totalPage;
	}

	public Integer getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(Integer totalNum) {
		this.totalNum = totalNum;
	}

	public Boolean getIsMore() {
		return isMore;
	}

	public void setIsMore(Boolean isMore) {
		this.isMore = isMore;
	}

	public Integer getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}

	public Integer getStartIndex() {
		return startIndex;
	}

	public void setStartIndex(Integer startIndex) {
		this.startIndex = startIndex;
	}

	public List<T> getItems() {
		return items;
	}

	public void setItems(List<T> items) {
		this.items = items;
	}
}
