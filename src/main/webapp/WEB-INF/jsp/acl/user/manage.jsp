<%@ page language="java" contentType="text/html;charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8">
<title>My Sample | 用户管理</title>

<%@ include file="/WEB-INF/jsp/common/include.jsp"%>
<%@ include file="/WEB-INF/jsp/common/sidebarInit.jsp"%>
<script>
	$(document).ready(function (){
		$("#pageQueryForm").manage({});
		
		$("#addUser").click(function() {
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
		
		$("#data-table").on("click", ".status_op", function(){
			var trNode = this.parentNode.parentNode;
			var hrefUrl = this.href;
			$.confirm({
		    	theme: 'white',
		        title: 'Are you sure',
		        content: '确定变更该用户状态？',
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
										$._handleTableData(data,"update");
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
			<h1 class="page-header">用户管理 <small>My place,My rule</small></h1>
			<!-- end page-header -->
			<!-- begin row -->
			<div class="row">
			    <!-- begin col-12 -->
			    <div class="col-md-12">
                    <!-- begin panel -->
                    <div class="panel panel-inverse">
                        <div class="panel-heading">
                            <div class="panel-heading-btn">
                                <a href="javascript:;" class="btn btn-xs btn-icon btn-circle btn-default" data-click="panel-expand"><i class="fa fa-expand"></i></a>
                                <a href="javascript:;" class="btn btn-xs btn-icon btn-circle btn-success" data-click="panel-reload"><i class="fa fa-repeat"></i></a>
                                <a href="javascript:;" class="btn btn-xs btn-icon btn-circle btn-warning" data-click="panel-collapse"><i class="fa fa-minus"></i></a>
                                <a href="javascript:;" class="btn btn-xs btn-icon btn-circle btn-danger" data-click="panel-remove"><i class="fa fa-times"></i></a>
                            </div>
                            <h4 class="panel-title">用户管理</h4>
                        </div>
                        <div id="myManager" class="panel-toolbar">
                        	<a id="addUser" href="${rootUrl }app/acl/user/addUser" class="btn btn-inverse btn-sm m-r-5 m-b-5">添加用户</a>
                        	<form:form class="form-horizontal form-inline" modelAttribute="queryModel"  id="pageQueryForm" name="pageQueryForm" method="post" action="${rootUrl}app/acl/user/manage">
	                        	<div class="form-group m-5">
                                    <label class="control-label">账号:</label>
                                    <input name="loginName" type="text" class="form-control" placeholder="账号" />
                                </div>
	                        	<div class="form-group m-5">
                                    <label class="control-label">昵称:</label>
                                    <input name="name" type="text" class="form-control" placeholder="昵称" />
                                </div>
	                        	<button type="submit" class="btn btn-inverse btn-sm m-r-5 m-b-5">查询</button>
                        	</form:form>
                        </div>
                        <div class="panel-body">
                            <table id="data-table" class="table table-striped table-bordered">
                                <thead>
                                    <tr>
                                        <th>账号</th>
                                        <th>昵称</th>
                                        <th>状态</th>
                                        <th>角色</th>
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
			    <!-- end col-12 -->
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