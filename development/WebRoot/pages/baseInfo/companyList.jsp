<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="com.tl.resource.business.dto.UserDto"%>
<%@page import="com.tl.common.util.LoginInforUtil"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	UserDto userDto = null;
	if (LoginInforUtil.getLoginInfor(request) != null) {
		userDto = LoginInforUtil.getLoginInfor(request).getUser();
	}
%>

<script>
var PATH = '<%=path%>';
var userId = '<%=userDto.getId()%>';
var userName = '<%=userDto.getUserName()%>';
var PAGESIZE = parseInt((Ext.getBody().getHeight()-250)/24);
</script>

<link rel="stylesheet" type="text/css" href="<%=path%>/extjs/plugins/SwfUploadPanel/SwfUploadPanel.css" />

<style type="text/css">
.ext-ie .x-row-editor .x-form-text {
	margin: 0 !important;
}

.x-row-editor-header {
	height: 2px;
	overflow: hidden;
	background: transparent url(<%=path%>/images/row-editor-bg.gif) repeat-x
		0 0;
}

.x-row-editor-footer {
	height: 2px;
	overflow: hidden;
	background: transparent url(<%=path%>/images/row-editor-bg.gif) repeat-x
		0 -2px;
}

.ext-ie .x-row-editor-footer {
	margin-top: -1px;
}

.x-row-editor-body {
	overflow: hidden;
	zoom: 1;
	background: #ebf2fb;
	padding-top: 2px;
}

.x-row-editor .x-btns {
	position: absolute;
	top: 28px;
	left: 20px;
	padding-left: 5px;
	background: transparent url(<%=path%>/images/row-editor-btns.gif)
		no-repeat 0 0;
}

.x-row-editor .x-btns .x-plain-bwrap {
	padding-right: 5px;
	background: transparent url(<%=path%>/images/row-editor-btns.gif)
		no-repeat right -31px;
}

.x-row-editor .x-btns .x-plain-body {
	background: transparent url(<%=path%>/images/row-editor-btns.gif)
		repeat-x 0 -62px;
	height: 31px;
}

.x-row-editor .x-btns .x-table-layout-cell {
	padding: 3px;
}

.icon-grid {
	background-image:
		url(<%=path%>/extjs/resources/images/icons/fam/grid.png) !important;
}

.align-center {
	margin: 0 auto; /* 居中 这个是必须的，，其它的属性非必须 */
	/*width:1000px;         给个宽度 顶到浏览器的两边就看不出居中效果了 */
}
</style>
<div id="searchConpany"></div>
<div id="companyViewPort"></div>
<div id="companyGird"></div>

<script type="text/javascript" src="<%=path%>/js/upload/ImageView.js"></script>
<script type="text/javascript" src="<%=path%>/js/upload/SlaveManager.js"></script>
<script type="text/javascript" src="<%=path%>/extjs/plugins/SwfUploadPanel/SwfUpload.js"></script>
<script type="text/javascript" src="<%=path%>/extjs/plugins/SwfUploadPanel/SwfUploadPanel.js"></script>
	
<!-- 公司信息列表 -->
<script type="text/javascript"
	src="<%=path%>/js/baseInfo/companyList.js"></script>







