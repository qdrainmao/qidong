<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!--引入jstl-->
<%@include file="common/tag.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <title>秒杀详情页</title>
    <%@include file="common/head.jsp"%>
</head>
<body>
    <div class="container">
        <div class="panel panel-default text-center">
            <div class="panel-heading">
                <h1>${seckill.name}</h1>
            </div>
            <div class="panel-body">
                <h2 class="text-danger">
                    <!--显示time图标-->
                    <span class="glyphicon glyphicon-time"></span>
                    <!--展示倒计时-->
                    <span class="glyphicon" id="seckill-box"></span>
                </h2>
            </div>
        </div>
    </div>
    <!--登录弹出层，输入电话-->
    <div id="killPhoneModal">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h3 class="modal-title text-header">
                        <span class="glyphicon glyphicon-phone"></span>秒杀电话：
                    </h3>
                </div>
                <div class="modal-body">
                    <div class="row">
                        <div class="col-xs-8 col-xs-offset-2">
                            <input type="text" name="killPhone" id="killPhoneKey" placeholder="填手机号哈哈" class="form-control">
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <!--验证消息-->
                    <span id="killPhoneMessage" class="glyphicon"></span>
                    <button type="button" id="killPhoneBtn" class="btn btn-success">
                        <span class="glyphicon glyphicon-phone"></span>
                        Submit
                    </button>
                </div>
            </div>
        </div>
    </div>
</body>

<!-- jQuery (Bootstrap 的 JavaScript 插件需要引入 jQuery) -->
<script src="http://apps.bdimg.com/libs/jquery/2.0.0/jquery.min.js"></script>
<!-- 包括所有已编译的插件 -->
<script src="http://apps.bdimg.com/libs/bootstrap/3.3.0/js/bootstrap.min.js"></script>

<!--使用CDN获取公共js http://www.bootcdn.cn/-->
<!--jQuery cookie操作插件-->
<script src="http://cdn.bootcss.com/jquery-cookie/1.4.1/jquery.cookie.min.js"></script>
<!--jQuery CountDown倒计时插件-->
<%--<script src="http://cdn.bootcss.com/jquery-countdown/2.1.0/jquery.countdown.min.js"></script>--%>
<script src="https://cdn.bootcss.com/jquery-countdown/2.0.2/jquery.countdown-zh-CN.min.js"></script>
<!--开始编写交互逻辑
    格式必须<script src="" type="text/javascript"></script>不能<script src="" type="text/javascript"/>
-->
<script src="/resources/script/seckill.js" type="text/javascript"></script>
<script type="text/javascript">
    $(function () {
        //el表达式传入参数
        seckill.detail.init({
            seckillId : ${seckill.seckillId},
            startTime : ${seckill.startTime.time},//转毫秒
            endTime : ${seckill.endTime.time}
        });
    });
</script>
</html>