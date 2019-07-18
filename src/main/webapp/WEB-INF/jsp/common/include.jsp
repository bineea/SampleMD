<%@ page language="java" contentType="text/html;charset=utf-8"
    pageEncoding="utf-8" session="true"%>
<%@ include file="tag.jsp"%>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1" />
<!-- ================== BEGIN BASE CSS STYLE ================== -->
<link href="${rootUrl}assets/plugins/jquery-ui/themes/base/minified/jquery-ui.min.css" rel="stylesheet" />
<link href="${rootUrl}assets/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" />
<link href="${rootUrl}assets/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet" />
<link href="${rootUrl}assets/css/animate.min.css" rel="stylesheet" />
<link href="${rootUrl}assets/css/style.min.css" rel="stylesheet" />
<link href="${rootUrl}assets/css/style-responsive.min.css" rel="stylesheet" />
<link href="${rootUrl}assets/css/theme/default.css" rel="stylesheet" id="theme" />
<link href="${rootUrl}assets/plugins/jquery-confirm/dist/jquery-confirm.min.css" rel="stylesheet"/>
<!-- ================== END BASE CSS STYLE ================== -->

<!-- ================== BEGIN BASE JS ================== -->
<script src="${rootUrl}assets/plugins/pace/pace.min.js"></script>
<script src="${rootUrl}assets/plugins/jquery/jquery-1.9.1.min.js"></script>
<script src="${rootUrl}assets/plugins/jquery/jquery-migrate-1.1.0.min.js"></script>
<script src="${rootUrl}assets/plugins/jquery-ui/ui/minified/jquery-ui.min.js"></script>
<script src="${rootUrl}assets/plugins/bootstrap/js/bootstrap.min.js"></script>
<!--[if lt IE 9]>
	<script src="${rootUrl}assets/crossbrowserjs/html5shiv.js"></script>
	<script src="${rootUrl}assets/crossbrowserjs/respond.min.js"></script>
	<script src="${rootUrl}assets/crossbrowserjs/excanvas.min.js"></script>
<![endif]-->
<script src="${rootUrl}assets/plugins/slimscroll/jquery.slimscroll.min.js"></script>
<script src="${rootUrl}assets/plugins/jquery-cookie/jquery.cookie.js"></script>
<script src="${rootUrl}assets/plugins/jquery-confirm/dist/jquery-confirm.min.js"></script>
<script src="${rootUrl}assets/js/base64.js"></script>
<script src="${rootUrl}assets/js/jquery/jquery.form.js"></script>
<script src="${rootUrl}assets/js/jquery/jquery.custom-1.0.js"></script>
<script src="${rootUrl}assets/js/apps.min.js"></script>
<!-- ================== END BASE JS ================== -->

<script>
$(document).ready(function() {
	$("#modProfile,#modPasswd").click(function(){
		$.ajax({
			url: this.href,
			success: function(data, textStatus, jqXHR) {
				$._showModal({size:"modal-lg",backdrop:true},data);
			},
			error:function(XMLHttpRequest, textStatus, errorThrown) {
				$.showWarnMsg("系统异常，请稍后重试！");
			}
		});
		return false;
	});
});
</script>