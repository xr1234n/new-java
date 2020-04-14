package com.wwx.dao;

import java.util.List;

import com.wwx.pojo.Experience;
import com.wwx.pojo.ajax.ExperienceAjax;
import org.apache.ibatis.annotations.Param;

import com.wwx.pojo.Post;
import com.wwx.pojo.Profession;
import com.wwx.pojo.ajax.OpinionAjax;

public interface PoSql {
	// 查看岗位是否存在
	public String findPo(Integer poId);
	//返回图表所需数据
	public Post findPoCi(Integer poId);
	//此岗位数
	public Integer findThis(Integer poId);
	//其它岗位数
	public Integer findOther(@Param("id")Integer poId,@Param("list")List<Profession> list);
	//平均工资
	public Integer findWage(Integer poId);
	//相关专业
	public List<Profession> findPr(Integer poId);
	//相关课程
	public List<String> findCourse(Integer poId);
	//是否存在岗位关联
	public Integer findRelated(Integer poId,Integer oldId);
	//关联岗位信息
	public Post findRePo(Integer poId);
	//审核通过的经验
	public List<Experience> findExperience(String poName);
	//当天是否提交过经验
	public Integer findExpSubmit(ExperienceAjax exp);
	//新增待审核经验
	public void addExperience(ExperienceAjax exp);
	//新增关联
	public void addRelated(Integer poId,Integer oldId);
	//新增建议
	public void addOpinion(OpinionAjax ajax);
	//增加关联度
	public void upRelated(Integer poId,Integer oldId);
}