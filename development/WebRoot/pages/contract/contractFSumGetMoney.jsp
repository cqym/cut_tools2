<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
	<script type="text/javascript" src="<%=path %>/js/common.js"></script>   
    <script type="text/javascript" src="<%=path %>/extjs/ext-lang-zh_CN.js"></script>   

      <script type="text/javascript" src="<%=path %>/js/contractFSumGetMoney/updateContractFSumGetMoney.js"></script>
	  <script type="text/javascript" src="<%=path %>/js/contractFSumGetMoney/addContractFSumGetMoney.js"></script>
	<script type="text/javascript" src="<%=path %>/js/contractFSumGetMoney/contract.js"></script>
 <script type="text/javascript" src="<%=path %>/js/contractFSumGetMoney/contractFSumGetMoney.js"></script>

<div id="contractFSumGetMoney"></div>
