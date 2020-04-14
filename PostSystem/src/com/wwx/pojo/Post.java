package com.wwx.pojo;

import java.util.List;

public class Post {
	private Integer poId;
	private String poName;
	private Integer poWage;         //此岗位整体的工资水平
	private Integer poNumber;       //此岗位整体的数量
	private List<City> ciList;      //岗位所在城市
	
	public List<City> getCiList() {
		return ciList;
	}
	public void setCiList(List<City> ciList) {
		this.ciList = ciList;
	}
	public Integer getPoWage() {
		return poWage;
	}
	public void setPoWage(Integer poWage) {
		this.poWage = poWage;
	}
	public Integer getPoNumber() {
		return poNumber;
	}
	public void setPoNumber(Integer poNumber) {
		this.poNumber = poNumber;
	}
	public Integer getPoId() {
		return poId;
	}
	public void setPoId(Integer poId) {
		this.poId = poId;
	}
	public String getPoName() {
		return poName;
	}
	public void setPoName(String poName) {
		this.poName = poName;
	}	
}
