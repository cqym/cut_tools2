<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="com.tl.resource.business.dto.UserDto"%>
<%@page import="com.tl.common.util.LoginInforUtil"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
UserDto userDto = null;
if(LoginInforUtil.getLoginInfor(request) != null) {
	userDto = LoginInforUtil.getLoginInfor(request).getUser();
}
%>
<script>
var PAGESIZE = parseInt((Ext.getBody().getHeight()-20)/24);
var userId = '<%=userDto.getId()%>';
var userName = '<%=userDto.getUserName()%>';
</script>
<link rel="stylesheet" type="text/css" href="<%=path%>/extjs/plugins/SwfUploadPanel/SwfUploadPanel.css" />
<link rel="stylesheet" type="text/css" href="<%=path%>/css/file-upload.css" />
    <script type="text/javascript" src="<%=path%>/extjs/plugins/SwfUploadPanel/SwfUpload.js"></script>
    <script type="text/javascript" src="<%=path%>/js/upload/ImageView.js"></script>
    <script type="text/javascript" src="<%=path%>/js/proToolsInfor/ColumnNodeUI.js"></script>
	<script type="text/javascript" src="<%=path %>/extjs/src/locale/ext-lang-zh_CN.js" charset="utf-8"></script>
	<script type="text/javascript" src="<%=path%>/js/proToolsInfor/treeSerializer.js"></script>
	<script type="text/javascript" src="<%=path%>/js/proToolsInfor/proTools_index.js"></script>
	<script type="text/javascript" src="<%=path%>/js/upload/SlaveManager.js"></script>
	<script type="text/javascript" src="<%=path%>/extjs/plugins/SwfUploadPanel/SwfUploadPanel.js"></script>
	<script type="text/javascript" src="<%=path%>/js/proToolsInfor/ImportProTools.js"></script>
	<script type="text/javascript" src="<%=path%>/extjs/plugins/ux/FileUploadField.js"></script>
   <div id="pro_tree-ct" style="text-align:left"></div>
