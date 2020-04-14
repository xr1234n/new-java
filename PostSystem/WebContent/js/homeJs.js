let button,myEvent=13; // 搜索按钮，回车按键对应码
$(document).ready(function () {

	// var : 全局变量 与 函数内的局部变量。 function xx(){} 函数，外不能使用。
	// let : 块级变量。 {} 语句块，外不能使用。
	// const : 常量。

	// 通过cookie判断是否是第一次进入网页
	let prompt = $.cookie("prompt");
	if (!prompt) {
		// 显示帮助说明
		$("#myModal").modal("show");

		$.cookie("prompt",true);
		// 也可以使用 window.sessionStorage.setItem('xx',xx) 实现这个功能，但有少许不同
		// 此处的sessionStorage为本地存储对象，而非服务端的session作用域对象
	}

	//搜索框
	button = $("#myButton");
	button.click(function () {
		search();
		toHomeAjax();
	});
	//按下键盘时的事件
	$(document).keydown(function (event) {
		// event.which 返回按键值
		if (event.which === myEvent) {
			search();
			toHomeAjax();
		}
	});

	//隐藏输入框提示
	$("#myForm").click(function () {
		$(".alert-warning").css("display", "none");
	});

	//感谢反馈
	$(".btn-success").click(function () {
		alert("很高兴能帮助到您，希望您能将此系统推荐给周围的人。")
	})
});

// 禁用搜索按钮2s
function search() {
	button.prop('disabled', true); //禁用按钮
	myEvent=-1;
	setTimeout(function () {
		button.prop('disabled', false); //回复按钮
		myEvent=13;
	},2000);
}

// 搜索专业并跳转
function toHomeAjax() {
	let text = $("#myForm").val();
	$.ajax({
		url: "homeAjax",
		type: "post",
		data: JSON.stringify({
			prName: text //发送数据
		}),
		contentType: "application/json;charset=UTF-8",

		success: function (data) {
			if (data.status === -1) {
				$(".alert-warning").css("display", "block");
			} else {
				let date = new Date(); // 获取当前时间对象
				//重定向
				window.location = "list?prId=" + data.prId+"&t="+date.getTime();
			}
		}
	})
}

// 上传意见
function toOpinionAjax() {
	//意见内容
	let text = $("textarea").val();
	$.ajax({
		url: "opinionAjax",
		type: "post",
		data: JSON.stringify({
			//发送数据
			type: "系统意见",
			content: text
		}),
		contentType: "application/json;charset=UTF-8",

		success: function () {
			//主动关闭模态框
			$("#myOpinion").modal('hide');
			alert("提交成功，谢谢您的反馈。");
		}
	})
}