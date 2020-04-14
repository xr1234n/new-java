package com.wwx.pojo;

import java.util.List;

public class Profession {
	private Integer prId;
	private String prName;
	private String manual;
	private List<City> ciList;     //专业可就业城市

	public String getManual() {
		return manual;
	}
	public void setManual(String manual) {
		this.manual = manual;
	}
	public List<City> getCiList() {
		return ciList;
	}
	public void setCiList(List<City> ciList) {
		this.ciList = ciList;
	}
	public Integer getPrId() {
		return prId;
	}
	public void setPrId(Integer prId) {
		this.prId = prId;
	}
	public String getPrName() {
		return prName;
	}
	public void setPrName(String prName) {
		this.prName = prName;
	}
}
