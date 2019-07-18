<%@ page language="java" contentType="text/html;charset=utf-8"
    pageEncoding="utf-8"%>

<!-- begin #sidebar -->
<div id="sidebar" class="sidebar">
	<!-- begin sidebar scrollbar -->
	<div data-scrollbar="true" data-height="100%">
		<!-- begin sidebar user -->
		<ul class="nav">
			<li class="nav-profile">
				<div class="image">
					<a href="javascript:;"><img src="${rootUrl }app/acl/user/showProfilePic/${loginUser.id}" alt="头像" /></a>
				</div>
				<div class="info">
					${loginUser.name}
					<small>My place,My rule</small>
				</div>
			</li>
		</ul>
		<!-- end sidebar user -->
		<!-- begin sidebar nav -->
		<ul class="nav" id="sidebarNav">
			<li class="nav-header">Navigation</li>
			<li class="has-sub">
				<a href="${rootUrl }app/index?myMenuId=index">
				    <b class="pull-right"></b>
				    <i class="fa fa-home"></i>
				    <span>首页</span>
			    </a>
			</li>
<!-- 	        begin sidebar minify button -->
<!-- 			<li><a href="javascript:;" class="sidebar-minify-btn" data-click="sidebar-minify"><i class="fa fa-angle-double-left"></i></a></li> -->
<!-- 	        end sidebar minify button -->
		</ul>
		<!-- end sidebar nav -->
	</div>
	<!-- end sidebar scrollbar -->
</div>
<div class="sidebar-bg"></div>
<!-- end #sidebar -->