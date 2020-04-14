package com.wwx.biz.page;

import org.springframework.stereotype.Service;

import com.wwx.pojo.ajax.ListAjax;

@Service
public class MyPage {
	private Integer c_page; //记录当前页
	private Integer t_page; //总页数
	private Integer l_page; //分页栏开始页数
	private Integer r_page; //分页栏结尾页数
	
	//计算数据库偏移量
	public Integer setOffset(ListAjax ajax) {
		c_page = ajax.getPageCurrent();
		c_page = (c_page-1)*ajax.getPageSize();
		return c_page;
	}
	//计算总页数
	public void setSum(ListAjax ajax,int sum) {
		//向上取整
		t_page = (int) Math.ceil((double)sum/ajax.getPageSize());
	}
	
	//控制当前页越界
	public void setCurrent(ListAjax ajax) {
		c_page = ajax.getPageCurrent();
		if(c_page<=0) {
			c_page=1;
		} else if (c_page>t_page && t_page!=0) { //当前页不能为0，否则会导致偏移量出现负数报错
			c_page=t_page;
		}
		ajax.setPageCurrent(c_page);
	}
	
	//计算分页栏,两端数字变化
	public void setPage(ListAjax ajax) {
		c_page = ajax.getPageCurrent();

		// 当总页数大于5时
		if(t_page>5) {
			if(c_page-2>0 && c_page+2<=t_page) {
				l_page=c_page-2;
				r_page=c_page+2;
			} else if(c_page-2<0) {      //1~3之间变动,分页栏数字不应变化
				l_page=1;
				r_page=5;
			} else if(c_page+2>t_page) { //尾页~尾页-2之间变动,分页栏数字不应变化
				l_page=t_page-4;
				r_page=t_page;
			}
		} else {
			l_page=1;
			r_page=t_page;
		}
		ajax.setPageStart(l_page);
		ajax.setPageEnd(r_page);
	}
}