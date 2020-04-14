package com.wwx.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wwx.biz.MyService;
import com.wwx.pojo.Profession;
import com.wwx.pojo.ajax.CityAjax;
import com.wwx.pojo.ajax.ListAjax;

@Controller
public class ListController {
	
	@Autowired
	private MyService ser;
	
	@RequestMapping("/listAjax")
	@ResponseBody
	public ListAjax listJson(@RequestBody ListAjax ajax) {
		// 更新岗位列表
		ser.findPrPo(ajax);
		return ajax;
	}
	
	@RequestMapping("/cityAjax")
	@ResponseBody
	public CityAjax cityjson(@RequestBody CityAjax ajax,HttpSession session) {
		Integer prId = (Integer) session.getAttribute("prId");
		Profession pr = ser.findPrCi(prId);
		ajax.setPr(pr);
		ajax.setRecommend(ser.findRecommend(prId));
		return ajax;
	}
	
	@RequestMapping("/list")
	public String toList(Integer prId, HttpSession session, HttpServletRequest request) {
		session.setAttribute("prId", prId);
		// 添加拦截器判断参数
		request.setAttribute("prName",ser.findPr(prId));
		return "list";
	}
}