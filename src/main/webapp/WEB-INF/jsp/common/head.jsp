<%@ page language="java" contentType="text/html;charset=utf-8"
    pageEncoding="utf-8"%>

<!-- begin #header -->
<div id="header" class="header navbar navbar-default navbar-fixed-top">
	<!-- begin container-fluid -->
	<div class="container-fluid">
		<!-- begin mobile sidebar expand / collapse button -->
		<div class="navbar-header">
			<a href="index.html" class="navbar-brand"><span class="navbar-logo"></span>My Sample</a>
			<button type="button" class="navbar-toggle" data-click="sidebar-toggled">
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
			</button>
		</div>
		<!-- end mobile sidebar expand / collapse button -->
		
		<!-- begin header navigation right -->
		<ul class="nav navbar-nav navbar-right">
			<li class="dropdown navbar-user">
				<a href="javascript:;" class="dropdown-toggle" data-toggle="dropdown">
					<img src="${rootUrl }app/acl/user/showProfilePic/${loginUser.id}" alt="" /> 
					<span class="hidden-xs">${loginUser.name }</span> <b class="caret"></b>
				</a>
				<ul class="dropdown-menu animated fadeInLeft">
					<li class="arrow"></li>
					<li><a id="modProfile" href="${rootUrl }app/modProfile">Edit Profile</a></li>
					<li><a id="modPasswd" href="${rootUrl }app/modPasswd">Modify Password</a></li>
					<li class="divider"></li>
					<li><a href="${rootUrl }app/common/logout">Log Out</a></li>
				</ul>
			</li>
		</ul>
		<!-- end header navigation right -->
	</div>
	<!-- end container-fluid -->
</div>
<!-- end #header -->