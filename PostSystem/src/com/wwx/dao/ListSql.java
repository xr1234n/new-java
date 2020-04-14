package com.wwx.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.wwx.pojo.Post;
import com.wwx.pojo.Profession;

public interface ListSql {
	//专业信息城市信息
	public Profession findPrCi(Integer prId);
	//推荐搜索
	public List<String> findRecommend(Integer prId);
	
	//岗位信息（多参数传递使用@Param为参数命名）
	public List<Post> findPrPo(@Param("pageCurrent")Integer pageCurrent,
			@Param("pageSize")Integer pageSize,@Param("prId")Integer prId,
			@Param("cName")String cName,@Param("sort")String sort,
			@Param("keywords")String keywords);
	
	//统计岗位记录数
	public Integer findpoCount(@Param("prId")Integer prId,
			@Param("cName")String cName,@Param("keywords")String keywords);
	
	//增加关键字热度
	public void addKeyword(String keywords);
}
