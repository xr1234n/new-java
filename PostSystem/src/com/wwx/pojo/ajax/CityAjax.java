package com.wwx.pojo.ajax;

import java.util.List;

import com.wwx.pojo.Profession;

public class CityAjax {
	private Profession pr;      //专业对象
	private List<String> recommend; //搜索推荐
	
	public List<String> getRecommend() {
		return recommend;
	}
	public void setRecommend(List<String> recommend) {
		this.recommend = recommend;
	}
	public Profession getPr() {
		return pr;
	}
	public void setPr(Profession pr) {
		this.pr = pr;
	}
}
