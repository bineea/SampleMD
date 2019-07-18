<%@ page language="java" contentType="text/html;charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8">
<title>My Sample | Welcome</title>
<%@ include file="/WEB-INF/jsp/common/include.jsp"%>
<%@ include file="/WEB-INF/jsp/common/sidebarInit.jsp"%>

<!-- ================== BEGIN PAGE LEVEL CSS STYLE ================== -->
<link href="${rootUrl}assets/plugins/jquery-jvectormap/jquery-jvectormap.css" rel="stylesheet" />
<link href="${rootUrl}assets/plugins/bootstrap-calendar/css/bootstrap_calendar.css" rel="stylesheet" />
<link href="${rootUrl}assets/plugins/gritter/css/jquery.gritter.css" rel="stylesheet" />
<link href="${rootUrl}assets/plugins/morris/morris.css" rel="stylesheet" />
<!-- ================== END PAGE LEVEL CSS STYLE ================== -->

<!-- ================== BEGIN PAGE LEVEL JS ================== -->
<script src="${rootUrl}assets/plugins/morris/raphael.min.js"></script>
<script src="${rootUrl}assets/plugins/morris/morris.js"></script>
<script src="${rootUrl}assets/plugins/jquery-jvectormap/jquery-jvectormap.min.js"></script>
<script src="${rootUrl}assets/plugins/jquery-jvectormap/jquery-jvectormap-world-merc-en.js"></script>
<script src="${rootUrl}assets/plugins/bootstrap-calendar/js/bootstrap_calendar.min.js"></script>
<script src="${rootUrl}assets/plugins/gritter/js/jquery.gritter.js"></script>
<script src="${rootUrl}assets/js/dashboard-v2.min.js"></script>
<!-- ================== END PAGE LEVEL JS ================== -->

<script>
	$(document).ready(function() {
	});
</script>
</head>
<body>
	<!-- begin #page-loader -->
	<div id="page-loader" class="fade in"><span class="spinner"></span></div>
	<!-- end #page-loader -->
	
	<!-- begin #page-container -->
	<div id="page-container" class="fade page-sidebar-fixed page-header-fixed">
		<%@ include file="/WEB-INF/jsp/common/head.jsp"%>

		<%@ include file="/WEB-INF/jsp/common/sidebar.jsp"%>
		
		<!-- begin #content -->
		<div id="content" class="content">
		</div>
		<!-- end #content -->
		
		<!-- begin scroll to top btn -->
		<a href="javascript:;" class="btn btn-icon btn-circle btn-success btn-scroll-to-top fade" data-click="scroll-top"><i class="fa fa-angle-up"></i></a>
		<!-- end scroll to top btn -->
	</div>
	<!-- end page container -->
</body>
</html>