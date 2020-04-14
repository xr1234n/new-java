package com.wwx.biz;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.wwx.pojo.Profession;
import com.wwx.pojo.ajax.ExperienceAjax;
import com.wwx.pojo.ajax.ListAjax;
import com.wwx.pojo.ajax.OpinionAjax;
import com.wwx.pojo.ajax.PostAjax;

public interface MyService {
	//home
	Integer findPr(String name);
	//list
	String findPr(Integer prId);
	Profession findPrCi(Integer prId);
	List<String> findRecommend(Integer prId);
	void findPrPo(ListAjax ajax);
	//post
	String findPo(Integer poId);
	void findPoCi(Integer poId, PostAjax ajax, HttpServletRequest request);
	void addRelated(Integer poId, Integer oldId);
	//新增经验
	void addExperience(ExperienceAjax exp);
    //post&home
	void addOpinion(OpinionAjax ajax);
}