<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

	
	 <!-- 树状信息显示插件 -->
	<script type="text/javascript" src="<%=path %>/js/proToolsInfor/treeSerializer.js"></script>
	
	
	
	 <!-- 新增报价单动态panel插件 -->
	<script type="text/javascript" src="<%=path %>/js/quotation/projectQuo/TabCloseMenu.js"></script>
	
	 <!-- 新增报价单动态panel插件 -->
	<script type="text/javascript" src="<%=path %>/js/quotation/projectQuo/RowEditor.js"></script>
	
	<style type="text/css">
		.icon-grid {
            background-image:url(<%=path%>/extjs/resources/images/icons/fam/grid.png) !important;
        }
        
		.align-center{   
		    margin:0 auto;      /* 居中 这个是必须的，，其它的属性非必须 */   
		   /*width:1000px;         给个宽度 顶到浏览器的两边就看不出居中效果了 */   
		}   
	</style>
	
	
		 
    
     <div id="productTreeDetail"></div>
   
     <div id="viewportZJ"></div>
     <div id="searchZJ"></div>
     <div id="gridZJ"></div>
  	 <div id="proTree-ctZJ"></div>
  	 <div id="proTree-ctZJ-edit" style="text-align:left"></div>
   	 <div id="salesPriceTreeZJ-ct"></div>
   	 <div id="salesPriceTreeZJ-ct-edit" style="text-align:left"></div>
   	
   	 
     <!-- 添加后产品列表Tree -->
     <div id="treeDeZJ"></div>
   	 <div id="treeDeZJEdit"></div>
   
   
   <script type="text/javascript" src="<%=path %>/js/quotation/projectQuo/projectQuoGrid.js"></script>
   	<!-- 新增报价单产品选择历史界面 -->
	<script type="text/javascript" src="<%=path %>/js/quotation/projectQuo/salesPriceHistory.js"></script>
    <!-- 新增报价单页面基本信息面板下拉列表项 -->
    <script type="text/javascript" src="<%=path %>/js/quotation/projectQuo/statusCombox.js"></script>
    
    <!-- 新增报价单页面数据计算 -->
    <script type="text/javascript" src="<%=path %>/js/quotation/projectQuo/calculate.js"></script>
   
    <script type="text/javascript" src="<%=path %>/js/quotation/projectQuo/productTreeEdit.js"></script>
	<script type="text/javascript" src="<%=path %>/js/quotation/projectQuo/salesPriceHistoryEdit.js"></script>
	<script type="text/javascript" src="<%=path %>/js/quotation/projectQuo/selectQuoProductWindowEdit.js"></script>
    <!-- 编辑报价单信息 -->
    <script type="text/javascript" src="<%=path %>/js/quotation/projectQuo/editQuoInfoWindow.js"></script>
   	
   	 <!-- 查看报价单详细信息 -->
    <script type="text/javascript" src="<%=path %>/js/quotation/projectQuo/ProQuoDetailWindow.js"></script>
   	
   
    <!-- 新增报价单产品选择产品部分 -->
    <script type="text/javascript" src="<%=path %>/js/quotation/projectQuo/productTree.js"></script>

	<!-- 新增报价单产品选择界面 -->
	<script type="text/javascript" src="<%=path %>/js/quotation/projectQuo/selectQuoProductWindow.js"></script>
      
   <!-- 新增报价单工序添加窗口 -->
   <script type="text/javascript" src="<%=path %>/js/quotation/projectQuo/addWorkOrderFormWindow.js"></script>
   <!-- 新增报价单页面工序对应的产品列表项 -->
   <script type="text/javascript" src="<%=path %>/js/quotation/projectQuo/workOrderProduct.js"></script>
   <!-- 新增报价单页面工序列表项 -->
   <script type="text/javascript" src="<%=path %>/js/quotation/projectQuo/workOrderList.js"></script>
  
   <!-- 新增报价单页面基本信息面板 -->
   <script type="text/javascript" src="<%=path %>/js/quotation/projectQuo/simpleForm.js"></script>
    <!-- 新增报价单页面 -->
    <script type="text/javascript" src="<%=path %>/js/quotation/projectQuo/quoOrderDetail.js"></script>
 	<!-- 客户信息列表 -->
 	<script type="text/javascript" src="<%=path %>/js/quotation/projectQuo/customerWindow.js"></script>
  
	<!-- 项目报价单列表页面 -->
  	<script type="text/javascript" src="<%=path %>/js/quotation/projectQuo/searchForm.js"></script>
  	
	<script type="text/javascript" src="<%=path %>/js/quotation/projectQuo/projectQuoList.js"></script>
	

	
	
	
	

