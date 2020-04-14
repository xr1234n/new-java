let i,testImg,btn,myTextarea; // i:按钮禁用剩余时间  testImg:图片对象  btn:提交意见按钮对象

$(document).ready(function () {
    btn = $(".myBtn2");
    myTextarea = $(".myTextarea");
    // 检查用户是否之前提交过意见
    btnTime(-1);

    testImg = $(".myTestImg");
    //打开意见提交（生成验证图片及角度）
    $(".myOpinion").click(function() {
        // 隐藏错误提示
        $(".alert").hide();
        // 清除旧输入内容
        myTextarea.val("");
        // 角度  随机生成 1-17 之间的数（ random()：返回 0 ~ 1 之间的随机数  ceil(x)：向上取整 ）
        let a = Math.round(Math.random()*16+1);
        // 随机读取图片
        let b = "img/test"+Math.round(Math.random()*3+1)+".png";
        // img添加图片并旋转
        testImg.attr("src",b).rotate(a*20);
    });

    //旋转按钮
    $(".along").click(function () {
        bounce();
        //获取当前角度
        let angle = testImg.getRotateAngle();
        //顺时针旋转10度
        testImg.rotate(Number(angle) + 20);
    });
    $(".inverse").click(function () {
        bounce();
        let angle = testImg.getRotateAngle();
        //逆时针旋转10度
        testImg.rotate(Number(angle) - 20);
    });

    //提交意见
    btn.click(function () {
        let angle = testImg.getRotateAngle();
        let text = $(".myTextarea").val();
        //替换空格为空 /xx/g:正则表达式全局匹配，\s:空白字符
        text = text.replace(/\s/g, "");
        if (angle % 360 !== 0) {
            //显示错误提示
            $(".myAlert2").show();
            //内容不能为空，不能小于10字
        } else if (text === '' || text.length<=10) {
            $(".myAlert1").show();
        } else {
            toOpinionAjax();
            btnTime(120); //120s后才能提交
        }
    });
});

// unload : 用户离开页面时触发
$(window).unload(function() {
    $.cookie("time",i);
});

//弹跳动画
function bounce() {
    testImg.animate({bottom: "20px"}, 200);
    testImg.animate({bottom: "0px"}, 200);
}

// 禁用提交按钮与计时
function btnTime(time) {
    // prop与attr一样用于设置或获取元素属性(非css属性)
    // attr从页面搜索获得元素值，所见即所得，不能正确取出boolean型数据
    // prop从属性对象中取值，不需要在页面中显示定义

    // 另一个写法 attr('disabled',disabled)
    btn.prop('disabled', true); //禁用按钮

    let cookie = $.cookie("time");
    // undefined : 未定义
    if(cookie!==undefined && cookie>0) {
        i=cookie;
    } else {
        i=time;
    }
    // setInterval : 循环计时器，需要手动设置停止
    let set = setInterval(function () {
        if(i>=0) {
            btn.text(i+"s");
            i--;
        } else {
            // 恢复按钮
            btn.text("提交");
            btn.prop('disabled', false);
            // 停止计时器
            clearInterval(set);
        }
    },1000);
}