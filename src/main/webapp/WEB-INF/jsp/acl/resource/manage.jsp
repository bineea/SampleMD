<%@ page language="java" contentType="text/html;charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8">
<title>My Sample | 资源管理</title>

<%@ include file="/WEB-INF/jsp/common/include.jsp"%>
<%@ include file="/WEB-INF/jsp/common/sidebarInit.jsp"%>
<!-- ================== BEGIN PAGE LEVEL CSS ================== -->
<link href="${rootUrl}assets/plugins/jstree/dist/themes/default/style.min.css" rel="stylesheet" />
<!-- ================== END PAGE LEVEL CSS ================== -->

<!-- ================== BEGIN PAGE LEVEL JS ================== -->
<script src="${rootUrl}assets/plugins/jstree/dist/jstree.min.js"></script>
<!-- ================== END PAGE LEVEL JS ================== -->

<script>
	$(document).ready(function() {
		var rootMenuJson = ${rootMenu};
		
		$('#jstree-default').jstree({
			'core':{
				'data':{
					'url':'${rootUrl}app/acl/resource/loadResource',
					'dataType':'json',
					'data':function(node){
						return { 'id' : node.id };
					}
				},
				'themes':{
					'responsive':false
				}
			},
			'types': {
	            'default': {
	                'icon': 'fa fa-folder text-warning fa-lg'
	            },
	            'file': {
	                'icon': 'fa fa-file text-inverse fa-lg'
	            },
	            'link': {
	            	'icon': 'fa fa-link fa-lg text-primary'
	            }
	        },
	        'plugins': ['types']
		});

		$('#jstree-default').on('changed.jstree', function (e, data) {
		    var menuType = data.instance.get_node(data.selected[0]).data;
		    if(menuType == 'COLUMN')
		    {
		    	$('#addColumn').attr("href","${rootUrl }app/acl/resource/addResource?menuType=COLUMN&isRootMenu=false&parentName="
		    			+data.instance.get_node(data.selected[0]).text+"&menuId="+data.selected[0]);
		    	$('#addMenu').attr("href","${rootUrl }app/acl/resource/addResource?menuType=MENU&isRootMenu=false&parentName="
		    			+data.instance.get_node(data.selected[0]).text+"&menuId="+data.selected[0]);
		    	$('#menuId').val(data.selected[0]);
		    	$('#pageQueryForm').submit();
		    	$('#addColumn').show();
		    	$('#addMenu').show();
		    }
		    else
		    {
		    	$('#addColumn').hide();
		    	$('#addMenu').hide();
		    	$('#menuId').val("");
		    	$('#addColumn').attr("href","javascript:;");
		    	$('#addMenu').attr("href","javascript:;");
		    }
		});
		
		$('#pageQueryForm').ajaxForm({
			type: "post", //提交方式 
			beforeSubmit: function(formData, jqForm, options) {
				var form = jqForm[0];
			    if(!form.menuId.value)
			    	return false;
			},
	        success: function (responseText, status, xhr) { //提交成功的回调函数
	        	var $responseText = $(responseText);
	        	//处理分页
	        	$._bindPager($responseText.find("#page_query_pager"));
	        	//显示列表
	        	$._handleSearchReasult($responseText);
	        },
	        error: function (xhr, status, error) {
	        	$.showWarnMsg("系统异常，请稍后重试！");
	        }
		});
		
		$("#page_pager").on("click", ".pager_item", function(){
			var pageclickednumber = $(this).attr("value");
			$._handlePageClick(pageclickednumber);
		});
		
		$("#addRootMenu").click(function(){
			$.ajax({
				url: this.href,
				success: function(data, textStatus, jqXHR) {
					$._showModal({},data);
				},
				error:function(XMLHttpRequest, textStatus, errorThrown) {
					$.showWarnMsg("系统异常，请稍后重试！");
				}
			});
			return false;
		});
		
		$("#addColumn").click(function(){
			$.ajax({
				url: this.href,
				success: function(data, textStatus, jqXHR) {
					$._showModal({},data);
				},
				error:function(XMLHttpRequest, textStatus, errorThrown) {
					$.showWarnMsg("系统异常，请稍后重试！");
				}
			});
			return false;
		});
		
		$("#addMenu").click(function(){
			$.ajax({
				url: this.href,
				success: function(data, textStatus, jqXHR) {
					$._showModal({},data);
				},
				error:function(XMLHttpRequest, textStatus, errorThrown) {
					$.showWarnMsg("系统异常，请稍后重试！");
				}
			});
			return false;
		});	
		
		$("#data-table").on("click", ".update_op", function(){
			$.ajax({
				url: this.href,
				success: function(data, textStatus, jqXHR) {
					$._showModal({},data);
				},
				error:function(XMLHttpRequest, textStatus, errorThrown) {
					$.showWarnMsg("系统异常，请稍后重试！");
				}
			});
			return false;
		});
		
		$("#data-table").on("click", ".delete_op", function(){
			var trNode = this.parentNode.parentNode;
			var hrefUrl = this.href;
			$.confirm({
		    	theme: 'white',
		        title: 'Are you sure',
		        content: '确定删除该数据？',
		        buttons: {   
		        	confirm: {
		            	text: '确认',
		                keys: ['enter'],
		                action: function(){
		 					$.ajax({
		 						url: hrefUrl,
		 						type: 'POST',
		 						success: function(data, textStatus, jqXHR) {
									if(jqXHR.getResponseHeader($.Constans.RESPONSE_HEADER_ERROR)) {
			 							$.showWarnMsg(data.msg);
									} else if(jqXHR.getResponseHeader($.Constans.RESPONSE_HEADER_NOTE)) {
										$._handleTableData(data,"delete",trNode);
										$('#jstree-default').jstree(true).refresh(); //刷新树
										$.showMsg(true,new Base64().decode(jqXHR.getResponseHeader($.Constans.RESPONSE_HEADER_NOTE)));
									}
		 						},
		 						error:function(XMLHttpRequest, textStatus, errorThrown) {
		 							$.showWarnMsg("系统异常，请稍后重试！");
		 						}
		 					});
		                }
		            },
		            cancel: {
		            	text: '取消'
		            }
		        }
		    });
			return false;
		});
	});
	
</script>

</head>
<body>
	<!-- begin #page-container -->
	<div id="page-container" class="fade page-sidebar-fixed page-header-fixed">
		<%@ include file="/WEB-INF/jsp/common/head.jsp"%>

		<%@ include file="/WEB-INF/jsp/common/sidebar.jsp"%>
		
		<!-- begin #content -->
		<div id="content" class="content">
			<!-- begin page-header -->
			<h1 class="page-header">资源管理 <small>My place,My rule</small></h1>
			<!-- end page-header -->
			<!-- begin row -->
			<div class="row">
			    <!-- begin col-3 -->
			    <div class="col-md-3">
	                <div id="jstree-default">
	                    <ul>
	                    </ul>
	                </div>
			    </div>
			    <!-- end col-3 -->
			    
			    <!-- begin col-9 -->
			    <div class="col-md-9">
                    <!-- begin panel -->
                    <div class="panel panel-inverse">
                        <div class="panel-heading">
                            <div class="panel-heading-btn">
                                <a href="javascript:;" class="btn btn-xs btn-icon btn-circle btn-default" data-click="panel-expand"><i class="fa fa-expand"></i></a>
                                <a href="javascript:;" class="btn btn-xs btn-icon btn-circle btn-success" data-click="panel-reload"><i class="fa fa-repeat"></i></a>
                                <a href="javascript:;" class="btn btn-xs btn-icon btn-circle btn-warning" data-click="panel-collapse"><i class="fa fa-minus"></i></a>
                                <a href="javascript:;" class="btn btn-xs btn-icon btn-circle btn-danger" data-click="panel-remove"><i class="fa fa-times"></i></a>
                            </div>
                            <h4 class="panel-title">资源管理</h4>
                        </div>
                        <div id="myManager" class="panel-toolbar">
                        	<a id="addRootMenu" href="${rootUrl }app/acl/resource/addResource?menuType=COLUMN&isRootMenu=true" class="btn btn-inverse btn-sm m-r-5 m-b-5">添加主栏目</a>
                        	<a id="addColumn" href="javascript:;" class="btn btn-inverse btn-sm m-r-5 m-b-5" style="display: none">添加子栏目</a>
                        	<a id="addMenu" href="javascript:;" class="btn btn-inverse btn-sm m-r-5 m-b-5" style="display: none">添加菜单</a>
                        	<a id="addNotMenu" href="javascript:;" class="btn btn-inverse btn-sm m-r-5 m-b-5" style="display: none">添加非菜单</a>
                        	<form:form class="form-horizontal form-inline" modelAttribute="queryModel"  id="pageQueryForm" name="pageQueryForm" method="post" action="${rootUrl}app/acl/resource/manage">
	                        	<div class="form-group m-5">
                                    <label class="control-label">资源名称:</label>
                                    <input name="name" type="text" class="form-control" placeholder="资源名称" />
                                </div>
                                <div class="form-group m-5">
									<label class="control-label">资源类型:</label>
									<select id="menuType" name="menuType" class="form-control">
										<option value="">----</option>
										<option value="COLUMN" style="display: none">栏目菜单</option>
										<option value="MENU">菜单</option>
										<option value="NOT_MENU">非菜单</option>
									</select>
								</div>
                                <input type="hidden" id="menuId" name="menuId" value="root"></input>
	                        	<button type="submit" class="btn btn-inverse btn-sm m-r-5 m-b-5">查询</button>
                        	</form:form>
                        </div>
                        <div class="panel-body">
                            <table id="data-table" class="table table-striped table-bordered">
                                <thead>
                                    <tr>
                                        <th>序号</th>
                                        <th>名称</th>
                                        <th>样式</th>
                                        <th>菜单类型</th>
                                        <th>URL</th>
                                        <th>请求方式</th>
                                        <th>操作</th>
                                    </tr>
                                </thead>
                                <tbody>
								</tbody>
                            </table>
                            <div id="page_pager">
						    </div>
                        </div>
                    </div>
                    <!-- end panel -->
			    </div>
			    <!-- end col-9 -->
			</div>
			<!-- end row -->
			
		</div>
		<!-- end #content -->
		
		<!-- begin scroll to top btn -->
		<a href="javascript:;" class="btn btn-icon btn-circle btn-success btn-scroll-to-top fade" data-click="scroll-top"><i class="fa fa-angle-up"></i></a>
		<!-- end scroll to top btn -->
	</div>
	<!-- end page container -->
</body>
</html>