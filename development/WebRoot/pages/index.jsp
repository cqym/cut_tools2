<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<meta name="keywords" content="" />
<meta name="description" content="" />
<meta name="Author" Content="" />
<meta name="Copyright" Content="" />
<title>刀具销售管理系统 登录</title>
<style type="text/css">
body{font-family:Arial, Helvetica, sans-serif,"宋体";font-size:12px;font-weight:normal;color:#636363;padding:0;margin:0 auto;}
div,p,ul,ol,li,h1,h2,h3,h4,h5,h6,table,tr,td,tbody,iframe,form,input{font-family:"宋体";padding:0;margin:0;}
table{border-collapse:collapse;}
select,input{font-family:"宋体";vertical-align:middle;}
img{border:0;padding:0;margin:0;}
ul,ol{list-style-type:none;}

.bodybg{background:url(<%=path%>/images/bodybg.jpg) left top repeat-x; background-color:#2398E4;}
.login_main{width:1003px; height:545px; background:url(<%=path%>/images/mainbg.jpg) left top no-repeat;  margin:0 auto; overflow:hidden;}
.login_tit{width:343px; height:38px; background:url(<%=path%>/images/system_name.gif) left top no-repeat; margin:114px 0 0 330px;}
.login_box{width:289px;/*358*/ height:134px;/*209*/ text-align:left; background:url(<%=path%>/images/loginbg.jpg) left top no-repeat; padding:75px 0 0 69px; margin:27px 0 0 324px; overflow:hidden;}
.login_box p{padding:0 0 12px 0;}
.login_box p input{width:143px; height:15px; font-size:12px; color:#595959; padding:2px 0 0 2px; margin-left:3px;}
.login_lspace{padding-left:51px;}
.login_lspace .btn{width:63px; height:23px; font-size:12px; color:#FFFFFF; line-height:25px; text-align:center; background:url(<%=path%>/images/btn.gif) left top no-repeat; border:0; overflow:hidden; cursor:pointer;}
.login_bg02{width:358px; height:89px; background:url(<%=path%>/images/loginbg02.jpg) left top no-repeat; margin:1px 0 0 324px;}
</style>
</head>

<body class="bodybg">

<div class="login_main">
	<div class="login_tit"></div>
	<div class="login_box">
	<form id="loginForm" name="loginForm" action="<%=path%>/loginAction.do" method="post">
	 <input type="hidden" name="ffc" value="login">
		<p>用户名：<input type="text" name="userName" value="" /></p>
		<p>密&nbsp;&nbsp;码：<input type="password" name="password" value="" /></p>
		<div class="login_lspace">
			<input type="submit" value="登 录" class="btn" />&nbsp;&nbsp;
			<input type="reset" value="取 消" class="btn" />
		</div>
	    </form>
	</div>
	<div class="login_bg02"></div>
</div>
</body>

</html>
<link rel="stylesheet" type="text/css" href="<%=path%>/extjs/resources/css/ext-all.css" />
<script type="text/javascript" src="<%=path%>/extjs/adapter/ext/ext-base.js"></script>
<script type="text/javascript" src="<%=path%>/extjs/ext-all.js"></script>
<SCRIPT LANGUAGE="JavaScript">
<!--
if(!Ext.isChrome) {
	Ext.Msg.show({
		title:'信息提示',
		msg: "建议使用Google浏览器!",
		width : 300,
		buttons: Ext.Msg.OK,
		icon: Ext.MessageBox.Info
	});
}
var inputs = document.getElementsByTagName("INPUT");
for(var i=0;i < inputs.length;i++){
    if(inputs[i].name == 'userName'){
	    inputs[i].focus();
	}
}
//-->
</SCRIPT>