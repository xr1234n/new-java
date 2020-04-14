package com.wwx.controller;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wwx.biz.MyService;
import com.wwx.pojo.ajax.HomeAjax;

@Controller
public class HomeController {
	
	@Autowired
	private MyService ser;
	
	@RequestMapping("/home")
	public String toHome() {
		return "home"; 
	}
	
	@RequestMapping("/homeAjax")
	@ResponseBody
	public HomeAjax toList(@RequestBody HomeAjax ajax) {
		String prName = ajax.getPrName();
		Integer prId = ser.findPr(prName);
		if(prId==null) {
			ajax.setStatus(-1);
		} else {
			ajax.setPrId(prId);
		}
		return ajax; 
	}
	
	@RequestMapping("/download")
	public ResponseEntity<byte[]> fileDownload(HttpServletRequest request) throws IOException {
		//从上下文中，取出项目在服务器的绝对路径，并在后添加给定路径
		String path = request.getServletContext().getRealPath("/file/可查询专业名称说明.pdf");
		//创建文件对象
		File file = new File(path);
		//创建响应头对象
		HttpHeaders headr = new HttpHeaders();
		//通知浏览器以下载方式打开文件(URLEncoder.encode设置编码格式，防止文件中文无法显示)
		headr.setContentDispositionFormData("attachment", URLEncoder.encode("可查询专业名称说明.pdf","UTF-8"));
		//定义以流形式下载
		headr.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		//使用mvc的ResponseEntity封装下载数据
		return new ResponseEntity<>(FileUtils.readFileToByteArray(file), headr, HttpStatus.OK);
	}
}
