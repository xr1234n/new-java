package com.wwx.biz.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.wwx.pojo.ajax.ExperienceAjax;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wwx.biz.MyService;
import com.wwx.biz.page.MyPage;
import com.wwx.dao.HomeSql;
import com.wwx.dao.ListSql;
import com.wwx.dao.PoSql;
import com.wwx.pojo.Post;
import com.wwx.pojo.Profession;
import com.wwx.pojo.ajax.ListAjax;
import com.wwx.pojo.ajax.OpinionAjax;
import com.wwx.pojo.ajax.PostAjax;

@Service
@Transactional //事务
public class MyServiceImpl implements MyService {
	
	@Autowired
	private ListSql lSql;
	@Autowired
	private PoSql pSql;
	@Autowired
	private HomeSql hSql;
	@Autowired
	private MyPage page;

	// 检查专业是否存在(首页)
	public Integer findPr(String name) {
		return hSql.findPr1(name);
	}
	// 检查专业是否存在(列表页)
	public String findPr(Integer prId) {
		return hSql.findPr2(prId);
	}

	// 检查岗位是否存在
	public String findPo(Integer poId) {
		return pSql.findPo(poId);
	}

	//专业以及包含城市
	public Profession findPrCi(Integer prId) {
		return lSql.findPrCi(prId);
	}

	//专业推荐
	public List<String> findRecommend(Integer prId) {
		return lSql.findRecommend(prId);
	}
	
	//岗位列表与页数
	public void findPrPo(ListAjax ajax) {
		
		//如果是搜索栏的请求则增加关键字搜索热度
		String key = ajax.getKeywords();
		if(ajax.getType()==0 && key!=null && !key.equals("")) {
			lSql.addKeyword(key);
		}
		
		//获取总记录数
		int sum = lSql.findpoCount(ajax.getPrId(),ajax.getcName(),key);

		//获取总页数(与当前页无关)
		page.setSum(ajax, sum);
		
		//控制越界,并修正当前页(需要先计算总页数)
		page.setCurrent(ajax);
		
		//计算查询所需的偏移量
		Integer offset = page.setOffset(ajax);
		
		//ajax返回岗位
		ajax.setPo(
			//查询岗位(myBatis返回list时没有查询结果是“空集合”而不是“null”)
			lSql.findPrPo(offset,ajax.getPageSize(),
					ajax.getPrId(),ajax.getcName(),ajax.getSort(),key)
		);

		//计算前端所需页码参数
		page.setPage(ajax);
	}
	
	//单个岗位详细数据
	public void findPoCi(Integer poId,PostAjax ajax,HttpServletRequest request) {
		//添加城市工资岗位数
		Post post = pSql.findPoCi(poId);
		ajax.setPoCity(post);

		//添加岗位课程
		ajax.setCourse(pSql.findCourse(poId));

		//岗位对应专业
		List<Profession> prName=pSql.findPr(poId);
		ajax.setPrName(prName);

		//其它岗位数
		Integer poOther = pSql.findOther(poId, prName);
		ajax.setPoOther(poOther);
		//自身岗位数
		Integer poThis = pSql.findThis(poId);
		ajax.setPoThis(poThis);

		//此处要注意int进行除法时小于0的数会直接为0
		if(poThis!=null && poOther!=null) {
			Integer proportion = (int)((float)poThis/(float)(poThis+poOther)*100);
			//添加岗位占比
			ajax.setProportion(proportion);
		} else if(poThis!=null) {
			ajax.setProportion(100); //只有一个岗位的情况
		}
		//添加整体平均工资
		ajax.setPoWage(pSql.findWage(poId));
		//添加推荐关联岗位
		ajax.setPoRe(pSql.findRePo(poId));
		//添加知识推荐
		ajax.setRecommend(this.getFile(post, request));
		//添加岗位经验
		ajax.setExp(pSql.findExperience(post.getPoName()));
	}
	
	//读取知识推荐文件
	public String getFile(Post post,HttpServletRequest request) {
		//从上下文中，取出项目在服务器的绝对路径，并在后添加给定路径
		String path = request.getServletContext().getRealPath("/file/"+post.getPoName()+".txt");
		File file=new File(path);
		//判断文件是否存在
		if (file.exists()) {
			StringBuffer recommend = new StringBuffer();
			try {
				//创建字节流输入对象
				FileInputStream in = new FileInputStream(path);
				//将“字节流”转为“字符流”输入对象，并设置编码
				InputStreamReader isr = new InputStreamReader(in,"UTF-8");
				//使用字符流包装类(此类提供了更高效的方法)
				BufferedReader br = new BufferedReader(isr);
				String row;
				// readLine : 读取一个文本行
	            while((row = br.readLine())!=null){
	            	//在末尾添加字符串
	            	recommend.append(row);
	            }
	            br.close();    
			} catch (IOException e) {
				e.printStackTrace();
			}
			return recommend.toString();
		} else {
			return "整理中";
		}
	}
	
	//推荐岗位关联
	public void addRelated(Integer poId,Integer oldId) {
		//此处要注意！包装类对比不能使用==
		if(poId!=null && oldId!=null && !poId.equals(oldId)) {
			//以前没有存在联系则建立联系
			if(pSql.findRelated(poId, oldId)==null) {
				pSql.addRelated(poId, oldId);
			} else {
				//有联系增加关联度
			    pSql.upRelated(poId, oldId);	
			}
		}
	}
	
	//新增建议
	public void addOpinion(OpinionAjax ajax) {
		pSql.addOpinion(ajax);
	}

	//新增经验
	public void addExperience(ExperienceAjax exp) {
		if(pSql.findExpSubmit(exp)==null) {
			exp.setType(0);
			pSql.addExperience(exp);
		} else {
			exp.setType(1);
		}
	}
}