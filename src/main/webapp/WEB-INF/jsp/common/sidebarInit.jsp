<%@ page language="java" contentType="text/html;charset=utf-8"
    pageEncoding="utf-8"%>
<script>
	$(document).ready(function() {
			$("#sidebarNav").append(toMenuHtml(JSON.parse('${roleMenu}'), true));
			$("#${currentResource.id}").parents("#sidebarNav li").andSelf().addClass("active");
			App.init();
	});
	
	function toMenuHtml(allMenus, hasLogo) {
		var menuHtml = '';
		for(var i=0; i<allMenus.length; i++) {
			if(allMenus[i].column) {
									  menuHtml += '<li class="has-sub" id="' + allMenus[i].resource.id + '">'
											   +	'<a href="javascript:;">';
				allMenus[i].hasColumns ? menuHtml += 	'<b class="caret pull-right"></b>' : menuHtml;
							hasLogo ? menuHtml += 		'<i class="' + allMenus[i].resource.logoStyle + '"></i>' : menuHtml;
									  menuHtml += 		'<span>' + allMenus[i].resource.name + '</span>'
											   + 	'</a>';
				allMenus[i].hasColumns ? menuHtml += '<ul class="sub-menu">'
											   +		toMenuHtml(allMenus[i].columnMenu, false)
											   +	'</ul>'
											   +  '</li>' 
									: menuHtml;
			}
			else {
				menuHtml += '<li id="' + allMenus[i].resource.id + '"><a href="${rootUrl }' + allMenus[i].resource.url + '">' + allMenus[i].resource.name + '</a></li>';
			}
		}
		return menuHtml;
	}
</script>