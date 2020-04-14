package com.wwx.pojo.ajax;

import java.util.List;

import com.wwx.pojo.Post;

public class ListAjax {	
	//发送数据
	private String keywords;         //关键字
	private String cName;            //城市名称
	private String sort;             //排序状态
	private Integer type=1;          //操作类型(0搜索,1筛选与翻页)
	private Integer prId;            //专业Id
	private final int pageSize=5;    //页面大小
	//接收数据
	private List<Post> po;           //列表集合
	private Integer pageStart;       //开始页数
	private Integer pageEnd;         //结束页数
	private Integer pageCurrent;     //当前页数
	
	
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getPrId() {
		return prId;
	}
	public void setPrId(Integer prId) {
		this.prId = prId;
	}
	public String getKeywords() {
		return keywords;
	}
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	public String getcName() {
		return cName;
	}
	public void setcName(String cName) {
		this.cName = cName;
	}
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	public int getPageSize() {
		return pageSize;
	}
	public Integer getPageStart() {
		return pageStart;
	}
	public void setPageStart(Integer pageStart) {
		this.pageStart = pageStart;
	}
	public Integer getPageEnd() {
		return pageEnd;
	}
	public void setPageEnd(Integer pageEnd) {
		this.pageEnd = pageEnd;
	}
	public Integer getPageCurrent() {
		return pageCurrent;
	}
	public void setPageCurrent(Integer pageCurrent) {
		this.pageCurrent = pageCurrent;
	}
	public List<Post> getPo() {
		return po;
	}
	public void setPo(List<Post> po) {
		this.po = po;
	}
}
