package com.wwx.pojo;

public class City {
	private Integer cId;
	private String cName;
	private Integer ciWage;    //此城市岗位的工资水平
	private Integer ciNumber;  //此城市岗位的数量
	
	public Integer getCiWage() {
		return ciWage;
	}
	public void setCiWage(Integer ciWage) {
		this.ciWage = ciWage;
	}
	public Integer getCiNumber() {
		return ciNumber;
	}
	public void setCiNumber(Integer ciNumber) {
		this.ciNumber = ciNumber;
	}
	public Integer getcId() {
		return cId;
	}
	public void setcId(Integer cId) {
		this.cId = cId;
	}
	public String getcName() {
		return cName;
	}
	public void setcName(String cName) {
		this.cName = cName;
	}
}
