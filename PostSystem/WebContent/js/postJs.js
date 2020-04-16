// 全局变量
let poName,marginLeft,myNav,DN,sum,countError;

$(document).ready(function () {

	toPostAjax(); //生成数据

	//侧边栏按钮
	myNav=$(".myNav");
	DN=$(".myDiv,.navbar-header");
	$(".btn-lg").click(function () {
		let display = myNav.css('display');
		// 判断元素是否隐藏
		if (display !== 'none') {
			// animate : 自定义动画 （这里的width不是css中的属性,而是方法中的属性,表示根据什么进行动画）
			// 'toggle' : 显示或隐藏 , 200为动画时间
			myNav.animate({ width: 'toggle' }, 200);
			DN.animate({ marginLeft: '0px' }, 200);
		} else {
			myNav.animate({ width: 'toggle' }, 200);
			$(".myDiv").animate({ marginLeft: marginLeft }, 200);
			$(".navbar-header").animate({ marginLeft: "239px" }, 200);
		}
	});
	myWindow();  //设置侧边栏显示方式
	TinyMCE();   //生成文本编辑器

	//分享经验
	$(".myCollapse").click(function () {
		getCount();
	});
	$(".myBtn1").click(function () {
		// 获取文本域纯文本内容，步骤 ： 获取编辑区js对象后转jq对象 ，获取文本 ，排除空格
		let text1 = $(tinymce.activeEditor.getBody()).text().replace(/\s/g, "");
		// 获取输入框文本
		let text2 = $(".myInput").val();

		// 由于使用了===需要注意元素类型的一致
		if(text2!==String(sum)) {
			countError.show();             //显示验证码错误提示
		} else if(text1 === "" || text1.length<200 || text1.length>2000){
			alert("内容不符合规范，请重新修改"); //显示提示
		} else {
			text2=tinymce.activeEditor.getContent(); //获取编辑器转换的文本内容
			toExperienceAjax(text2);
		}
	});

	//展开全文
	$(".myHide p").click(function () {
		$(".myHide").hide();
		$(".myPanel").css("height","100%");
	})
});

// 当窗口发生变化时
$(window).resize(function() {
	myWindow();
});

// 设置ajax默认属性
$.ajaxSetup({
	type: "post",
	contentType: "application/json;charset=UTF-8"
});

// 侧边栏显示设置
function myWindow() {
	if($(window).width()<1000) {
		myNav.hide(); //隐藏侧边导航
		DN.css("margin-left","0px"); //取消外边距
		marginLeft="0px";            //取消文本区动画
	} else {
		// 恢复设置
		myNav.show();
		DN.css("margin-left","239px");
		marginLeft="239px";
	}
}

//生成图表与其它信息
function toPostAjax() {
	//岗位标题、占比标题、工资标题
	let h1=$("h1"),p=$(".proportion"),wage=$(".wage");
	//课程,知识推荐,分享经验
	let course=$(".myCourse p"),re=$(".myRecommend"),ex=$(".myBack");
	//相关岗位推荐
	let a = $(".text-left a");
	$.ajax({
		url: "postAjax",
		data: JSON.stringify({
			//发送数据
			poWage: 0
		}),

		success: function (data) {
			poName = data.poCity.poName;
			//岗位信息
			let popr = " 岗位名称 : " + poName + " <small> 相关专业 : ";
			//相关专业
			let pr = data.prName;
			for (let i = 0; i < pr.length; i++) {
				popr = popr + "<a href=\"list?prId=" + pr[i].prId + "\">" + pr[i].prName + "</a>";
			}
			popr = popr + "</small>";
			h1.html(popr);

			p.html("总岗位占比 : 此岗位占" + data.proportion + "%");
			wage.html("各城市工资 : 整体平均工资" + data.poWage + "K");
			//取出集合
			let post = data.poCity.ciList;
			//生成饼图
			pie(data.poThis, data.poOther);
			//数组赋值
			let name = [], ciWage = [], ciNumber = [];
			for (let i = 0; i < post.length; i++) {
				ciNumber[i] = post[i].ciNumber;
				ciWage[i] = post[i].ciWage;
				name[i] = post[i].cName;
			}
			//生成条形图
			bar("#myChart2", name, ciWage, 1);
			bar("#myChart3", name, ciNumber, 0);

			//相关课程
			let co = data.course;
			popr = "";
			for (let i = 0; i < co.length; i++) {
				popr = popr +"<span class=\"label label-primary\">"+ co[i]+"</span>";
			}
			course.html(popr);

			//知识推荐
			re.html(data.recommend);
			//分享经验
			let exp=data.exp;
			popr = "";
			for(let i=0;i<exp.length;i++) {
				popr = popr + "<div class=\"media\"><div class=\"media-left\"><img src=\"img/user.png\"></div>"+
					"<div class=\"media-body\">"+exp[i].text+"<div class=\"text-right\"><p>"+exp[i].date+"</p></div></div></div>"
			}
			ex.html(popr);

			//其他人还看过
			let poRe = data.poRe;
			if(poRe!=null) {
				a.html(poRe.poName);
				a.attr("href","post?poId="+poRe.poId);
			} else {
				a.html("暂无");
			}
		}
	})
}

// 意见提交
function toOpinionAjax() {
	//意见内容
	let text = $("textarea").val();
	$.ajax({
		url: "opinionAjax",
		data: JSON.stringify({
			//发送数据
			type: poName,
			content: text
		}),

		success: function () {
			//主动关闭模态框
			$("#myOpinion").modal('hide');
			alert("提交成功，谢谢您的反馈。");
		}
	})
}

// 经验提交
function toExperienceAjax(text) {
	// 获取日期，年月日
	let date = new Date();
	let time = date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate();
	$.ajax({
		url: "ExperienceAjax",
		data: JSON.stringify({
			//发送数据
			poName: poName,
			ip: returnCitySN["cip"], //获取ip (需先在js导入搜狐接口)
			date: time,
			text: text
		}),

		success: function (data) {
			if(data.type===0) {
				alert("提交成功，请耐心等待审核通过。");
			} else {
				alert("您今天已经提交过经验,请明天在进行分享。");
			}
		}
	})
}


//生成饼图
function pie (poThis, poOther) {
	//配置数据
	let data = {
		labels: ['此岗位', '其它岗位'],               //标签
		datasets: [{
			data: [poThis, poOther],                 //数据
			backgroundColor: ["#FC9D99", "#337ab7"], //弧背景颜色
			borderWidth: "3"                         //弧边框宽度
		}]
	};
	Chart.defaults.global.defaultFontSize = 20; //全局文字大小

	//创建图形
	let myPieChart = new Chart($("#myChart1"), {
		type: "pie", //图形类型
		data: data,  //数据
		//配置
		options: {
			cutoutPercentage: 50, //饼图中心空洞大小
			//标签图例(谷歌翻译网页时显示的是“传说”。。。)
			legend: {
				position: "bottom"
			}
		}
	});
}

//生成条形图方法  参数 : 元素id名,数据数组,功能选择(0岗位1工资)
function bar (id, cName, cNumber, style) {
	let data = {
		labels: cName,               //标签
		datasets: [{
			data: cNumber, //数据
			backgroundColor: "#FC9D99", //弧背景颜色
		}]
	};
	Chart.defaults.global.defaultFontSize = 15; //全局文字大小

	let myBarChart = new Chart($(id), {
		type: "bar",
		data: data,
		options: {
			//标签图例(谷歌翻译网页时显示的是“传说”。。。)
			legend: {
				display: false
			},
			//坐标轴
			scales: {
				yAxes: [{	     //y轴
					ticks: {     //刻度线
						beginAtZero: true, //起始值为0
						//刻度线格式
						callback: function (value) {
							if (style === 1) {
								return value + "K";
							} else {
								return value;
							}
						}
					}
				}]
			}
		}
	})
}

// 生成富文本编辑器
function TinyMCE() {
	tinymce.init({
		selector: "#myTextarea", //选定生成文本域
		language: "zh_CN",       //设置为中文，需导入汉化包
		height: "350px"
	})
}

// 获取生成计算验证码
function getCount() {
	let a,b,count=$(".count");
	countError=$(".countError");
	a = Math.round(Math.random()*10);
	b = Math.round(Math.random()*10);
	count.html("<strong>"+a+"+"+b+"=?:</strong>"); //生成计算题
	sum=a+b;
	countError.hide(); //隐藏错误提示
}