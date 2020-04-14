package com.wwx.pojo.ajax;

import java.util.List;

import com.wwx.pojo.Experience;
import com.wwx.pojo.Post;
import com.wwx.pojo.Profession;

public class PostAjax {
	//接收数据
	private Integer proportion;    //岗位百分比
	private Integer poWage;        //岗位整体平均工资
	private Integer poOther;       //其余岗位总数
	private Integer poThis;        //此岗位总数 
	private Post poCity;              //此岗位信息及在各城市的工资与岗位数
	private Post poRe;                //推荐关联岗位
	private List<Profession> prName;  //岗位对应专业
	private List<String> course;      //岗位对应课程
	private List<Experience> exp;      //岗位经验
	private String recommend;         //岗位知识推荐

	public List<Experience> getExp() {
		return exp;
	}
	public void setExp(List<Experience> exp) {
		this.exp = exp;
	}
	public Post getPoRe() {
		return poRe;
	}
	public void setPoRe(Post poRe) {
		this.poRe = poRe;
	}
	public String getRecommend() {
		return recommend;
	}
	public void setRecommend(String recommend) {
		this.recommend = recommend;
	}
	public List<String> getCourse() {
		return course;
	}
	public void setCourse(List<String> course) {
		this.course = course;
	}
	public List<Profession> getPrName() {
		return prName;
	}
	public void setPrName(List<Profession> prName) {
		this.prName = prName;
	}
	public Integer getProportion() {
		return proportion;
	}
	public void setProportion(Integer proportion) {
		this.proportion = proportion;
	}
	public Integer getPoWage() {
		return poWage;
	}
	public void setPoWage(Integer poWage) {
		this.poWage = poWage;
	}
	public Integer getPoOther() {
		return poOther;
	}
	public void setPoOther(Integer poOther) {
		this.poOther = poOther;
	}
	public Integer getPoThis() {
		return poThis;
	}
	public void setPoThis(Integer poThis) {
		this.poThis = poThis;
	}
	public Post getPoCity() {
		return poCity;
	}
	public void setPoCity(Post poCity) {
		this.poCity = poCity;
	}
	
}
