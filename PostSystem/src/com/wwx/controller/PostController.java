package com.wwx.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.wwx.pojo.ajax.ExperienceAjax;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wwx.biz.MyService;
import com.wwx.pojo.ajax.PostAjax;
import com.wwx.pojo.ajax.OpinionAjax;

@Controller
public class PostController {
	@Autowired
	private MyService ser;

	@RequestMapping("/post")
	public String toPost(Integer poId, HttpSession session, HttpServletRequest request) {
		session.setAttribute("poId", poId);
		// 添加拦截器判断参数
		request.setAttribute("poName",ser.findPo(poId));
		return "post";
	}

	@RequestMapping("/postAjax")
	@ResponseBody
	public PostAjax postJson(@RequestBody PostAjax ajax,HttpSession session,HttpServletRequest request) {
		Integer poId = (Integer) session.getAttribute("poId");
		//增加关联岗位
		ser.addRelated(poId, (Integer)session.getAttribute("oldId"));
		//保存此次查询的岗位id
		session.setAttribute("oldId", poId);
		//查询岗位详细信息(request用于取出项目路径)
		ser.findPoCi(poId, ajax, request);
		return ajax;
	}
	
	@RequestMapping("/opinionAjax")
	@ResponseBody
	public OpinionAjax opinionJson(@RequestBody OpinionAjax ajax) {
		ser.addOpinion(ajax);
		return ajax;
	}

	@RequestMapping("/ExperienceAjax")
	@ResponseBody
	public ExperienceAjax experienceJson(@RequestBody ExperienceAjax ajax) {
		ser.addExperience(ajax);
		return ajax;
	}
}