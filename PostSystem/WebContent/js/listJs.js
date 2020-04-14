//全局变量，专业id，城市名称，排序，关键字
let prId, cName, sort, keywords, myEvent;
$(document).ready(function () {

	let page; // 当前页数

	//ajax异步请求加载一般运行在js加载完DOM结点后，ajax追加的结点绑定普通事件会失效
	//ajax异步请求中也不能给全局变量赋值，若想赋值需改为同步请求
	// ".on" 可对未来添加的结点进行事件绑定，引用document当前文档对象或已加载的父元素添加即可

	toCityAjax();                      //同步，程序必须等待服务器响应，才能发下一个请求
	toListAjax(1,1);  //异步，程序不用等待服务器响应，就可发下一个请求

	//分页栏数字
	let ul = $("ul");
	ul.on("click", ".pageNum", function () {
		page = $(this).text();
		toListAjax(page, 1, cName, sort, keywords);
	});
	//上页
	ul.on("click", ".pageOn", function () {
		page = $(".active.pageNum").text();
		//Number:转换数字对象,避免被识别为字符串
		toListAjax(Number(page) - 1, 1, cName, sort, keywords);
	});
	//下页
	ul.on("click", ".pageUnder", function () {
		page = $(".active.pageNum").text();
		toListAjax(Number(page) + 1, 1, cName, sort, keywords);
	});

	//城市
	$(".myCity").on("click", "li", function () {
		if ($(this).text() !== "更多") {
			//清除指定ul下的所有li的指定样式
			$(".myCity").find('li').removeClass('active');
			$(this).addClass("active");
			//获取选择li中的为以下class的元素
			cName = $("li.active.cName").text();
			toListAjax(1, 1, cName, sort, keywords);
		}
	});
	//排序
	$(".mySort").on("click", "li", function () {
		//siblings获取同级元素
		$(this).siblings('li').removeClass('active');
		$(this).addClass("active");
		sort = $("li.active.sort").text();
		toListAjax(1, 1, cName, sort, keywords);
	});
	//更多按钮
	let myUl = $(".myUl");
	myUl.on("click", ".btn-default", function () {
		//toggle切换隐藏显示方法
		$(".panel").toggle();
	});	//mouseleave: 鼠标移出元素事件
	myUl.on("mouseleave", ".panel", function () {
		$(".panel").toggle();
	});

	//搜索按钮与回车
	$(".btn-primary").click(function () {
		search();
		form();
	});
	$(document).keydown(function (event) {
		if (event.which === 13) {
			search();
			form();
		}
	});
	//推荐搜索关键字
	$(".list-inline").on("click","a",function() {
		$(".form-control").val($(this).text());
		search();
		form();
	});

	//查看按钮 (防止缓存导致的不向服务器传递数据)
	$(".table").on("click","a",function () {
		let date = new Date();  // 获取当前时间对象
		let a = $(this);        // 获取点击超链接对象
		let href = a.attr("href").split("&")[0]; //获取未改动时的url
		a.attr("href",href+"&t="+date.getTime());
	})
});

// 设置ajax默认属性
$.ajaxSetup({
	type: "post",
	contentType: "application/json;charset=UTF-8"
});

// 禁用搜索按钮2s
function search() {
	let but = $(".list-inline a,.btn-primary");
	but.prop('disabled', true); //禁用按钮
	myEvent=-1;
	setTimeout(function () {
		but.prop('disabled', false); //回复按钮
		myEvent=13;
	},2000);
}

//搜索岗位
function form() {
	//获取输入框值
	keywords = $(".form-control").val();
	//清除当前状态
	cName = sort = '';
	$(".myUl ").find("li").removeClass("active");
	//获取li中值为指定内容的元素
	$("li:contains('全国'),li:contains('默认')").addClass("active");
	//发起ajax
	toListAjax(1, 0, cName, sort, keywords);
}

//生成分页栏
function getPage(pageStart, pageEnd, pageCurrent, status) {
	let pageUl = $(".pagination.pagination-lg");

	//功能选择 0生成,1不生成
	if (status === 0) {
		//上页
		let myLi = "<li class=\"pageOn\"><a>&laquo;</a></li>";
		//数字
		for (let i = pageStart; i <= pageEnd; i++) {
			if (i === pageCurrent) {
				myLi = myLi + "<li class=\"active pageNum\"><a>" + i + "</a></li>";
			} else {
				myLi = myLi + "<li class=\"pageNum\"><a>" + i + "</a></li>";
			}
		}
		//下页
		myLi = myLi + "<li class=\"pageUnder\"><a>&raquo;</a></li>";
		//设置页面内容
		pageUl.html(myLi);
	} else {
		pageUl.html("");
	}
}

//生成岗位列表 
function toListAjax(pageCurrent, type, cName, sort, keywords) {
	let tbody = $("tbody");
	let input = $(".form-control");
	$.ajax({
		url: "listAjax",
		data: JSON.stringify({
			//发送数据
			pageCurrent: pageCurrent,
			cName: cName,
			sort: sort,
			keywords: keywords,
			prId: prId,
			type: type  //0搜索栏,1筛选框或页码栏
		}),

		success: function (data) {
			//输入框保留输入
			input.val(data.keywords);

			let po = data.po;
			//判断是否有岗位数据
			if (po.length > 0) {
				//生成列表
				let list="";
				for (let i = 0; i < po.length; i++) {
					list = list + "<tr><td>" + po[i].poName + "</td><td>"
						+ po[i].poWage + "K</td><td>" + po[i].poNumber +
						"</td><td><a href=\"post?poId=" + po[i].poId + "&\"" +
						" class=\"btn btn-primary\" target=\"_blank\">查看</a></td></tr>";
				}
				tbody.html(list);
				//0生成分页栏，1不生成
				getPage(data.pageStart, data.pageEnd, data.pageCurrent, 0);
			} else {
				tbody.html("<tr><td colspan=\"3\" class=\"text-center\"><h3>没有相关岗位</h3></td></tr>");
				getPage(data.pageStart, data.pageEnd, data.pageCurrent, 1);
			}
		}
	})
}

//生成城市列表
function toCityAjax() {
	let h1 = $("h1");
	let city = $("#city");
	let ul=$(".list-inline");
	$.ajax({
		url: "cityAjax",
		data: JSON.stringify({
			pr: null,     //发送数据
		}),
		async: false,  //设置ajax为同步请求

		success: function (data) {
			//保存专业id
			prId = data.pr.prId;

			//生成专业名
			h1.html(data.pr.prName + "专业<br><small id=\"white\">就业岗位如下</small>");

			//设置专业介绍网址
			$(".manual").attr("href",data.pr.manual);

			//生成推荐搜索
			let list="<li><h4>推荐搜索 :</h4></li>";
			let re=data.recommend;
			for(let i=0;i<re.length;i++) {
				list=list+"<li><h4><a>"+re[i]+"</a></h4></li>";
			}
			ul.html(list);

			//生成岗位地点
			let ci = data.pr.ciList;
			list = "<li class=\"active cName\"><a>全国</a></li>";
			//大于27个城市就生成更多按钮，否则不生成
			if (ci.length > 27) {
				for (let i = 0; i < 26; i++) {
					list = list + "<li class=\"cName\"><a>" + ci[i].cName + "</a></li>";
				}             //btn-default ：普通按钮
				list = list + "<li><button class=\"btn btn-default\">更多<span class=\"glyphicon glyphicon-chevron-down\"></span></button></li>" +
					//panel-default ：面板
					"<div class=\"panel panel-default\"><div class=\"panel-body\"><ul class=\"nav nav-pills myUl myMouse\">";
				for (let i = 26; i < ci.length; i++) {
					list = list + "<li class=\"cName\"><a>" + ci[i].cName + "</a></li>";
				}
				list = list + "</ul></div></div>";
			} else {
				for (let i = 0; i < ci.length; i++) {
					list = list + "<li class=\"cName\"><a>" + ci[i].cName + "</a></li>";
				}
			}
			city.html(list);
		}
	})
}