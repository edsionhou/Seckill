<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="tag.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>秒杀详情页</title>
<%@ include file="head.jsp"%>


</head>
<body>
	<div class="container">
		<div class="panel panel-default text-center">
			<div class="panel-heading">
				<h1>${seckill.nameHou }</h1>
			</div>


			<div class="penel-body">
				<h2 class="text-danger">
					<span class="glyphicon glyphicon-time"></span> 
					<span class="glyphicon" id="seckill-box"></span>
				</h2>
			</div>
		</div>
	</div>

	<%--登录弹出层 输入电话--%>
<div id="killPhoneModal" class="modal ">

    <div class="modal-dialog">

        <div class="modal-content">
            <div class="modal-header">
                <h3 class="modal-title text-center">
                    <span class="glyphicon glyphicon-phone"> </span>请填写自己的电话号码，我们将以此作为个人标记:
                </h3>
            </div>

            <div class="modal-body">
                <div class="row">
                    <div class="col-xs-8 col-xs-offset-2">
                        <input type="text" name="killPhone" id="killPhoneKey"
                               placeholder="填写手机号^o^" class="form-control">
                    </div>
                </div>
            </div>

            <div class="modal-footer">
                <%--验证信息--%>
                <span id="killPhoneMessage" class="glyphicon"> </span>
                <button type="button" id="killPhoneBtn" class="btn btn-success">
                    <span class="glyphicon glyphicon-phone"></span>
                    Submit
                </button>
            </div>

        </div>
    </div>

</div>
<div>
<h1 ></h1>
</div>

</body>

<%--jQery文件,务必在bootstrap.min.js之前引入--%>
<script src="http://apps.bdimg.com/libs/jquery/2.0.0/jquery.min.js"></script>
<script src="http://apps.bdimg.com/libs/bootstrap/3.3.0/js/bootstrap.min.js"></script>

<%--使用CDN 获取公共js http://www.bootcdn.cn/--%>


<!-- 从bootcdn 获取   jquery countdown倒计时插件 -->
<%--jQuery Cookie操作插件--%>
<script src="http://cdn.bootcss.com/jquery-cookie/1.4.1/jquery.cookie.min.js"></script>
<%--jQuery countDown倒计时插件--%>
<script src="http://cdn.bootcss.com/jquery.countdown/2.1.0/jquery.countdown.min.js"></script>

<!-- 开始编写交互逻辑 -->
<script src="${pageContext.request.contextPath}/resource/scripts/propath.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/resource/scripts/seckill.js" type="text/javascript"></script>
<
<script type="text/javascript">
		$(function(){
			//使用EL表达式传入参数
			
			seckill.detail.init({
				seckillId : ${seckill.seckillId},
				startTime : ${seckill.startTime.time},
				endTime : ${seckill.endTime.time}
			});
			
			
			
		});



</script>




</html>