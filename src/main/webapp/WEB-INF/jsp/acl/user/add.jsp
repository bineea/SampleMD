<%@ page language="java" contentType="text/html;charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/common/tag.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="expires" content="0" />
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />

<!-- ================== BEGIN PAGE LEVEL CSS ================== -->
<link href="${rootUrl}assets/plugins/bootstrap-select/bootstrap-select.min.css" rel="stylesheet" />
<!-- ================== END PAGE LEVEL CSS ================== -->

<!-- ================== BEGIN PAGE LEVEL JS ================== -->
<script src="${rootUrl}assets/plugins/bootstrap-select/bootstrap-select.min.js"></script>
<!-- ================== END PAGE LEVEL JS ================== -->
<script>
$(document).ready(function() {
	$('.selectpicker').selectpicker('render');
	$('#myForm').ajaxForm({
		type: "post", //提交方式 
		complete: function(xhr) {
			$._hideModal();
		},
		success: function(responseText, status, xhr){
			if(xhr.getResponseHeader($.Constans.RESPONSE_HEADER_ERROR)){
				$.showWarnMsg(responseText.msg);
			}else if(xhr.getResponseHeader($.Constans.RESPONSE_HEADER_NOTE)){
				$._handleTableData(responseText, "add");
				$.showMsg(true,new Base64().decode(xhr.getResponseHeader($.Constans.RESPONSE_HEADER_NOTE)));
			}
		},
		error: function(xhr, status, error) {
			$.showWarnMsg("系统异常，请稍后重试！");
		}
	});
});
</script>
</head>
<body>
	<div class="modal-header">
	  	<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	  	<h4 class="modal-title">添加用户</h4>
	</div>
	<form:form modelAttribute="addModel" id="myForm" name="myForm" cssClass="form-horizontal" action="${rootUrl }app/acl/user/addUser" method="post">
	<div class="modal-body">
		 <div class="form-group">
             <label class="col-md-3 control-label">账号</label>
             <div class="col-md-7">
                 <input name="loginName" type="text" class="form-control" placeholder="账号" />
             </div>
         </div>
         <div class="form-group">
             <label class="col-md-3 control-label">昵称</label>
             <div class="col-md-7">
                <input name="name" type="text" class="form-control" placeholder="昵称" />
             </div>
         </div>
         <div class="form-group">
             <label class="col-md-3 control-label">邮箱</label>
             <div class="col-md-7">
                 <input name="email" type="email" class="form-control" placeholder="邮箱" />
             </div>
         </div>
         <div class="form-group">
             <label class="col-md-3 control-label">角色</label>
             <div class="col-md-7">
				 <select name="roleId" class="form-control selectpicker" data-size="10" data-live-search="true" data-style="btn-inverse">
                    <option value="">请选择...</option>
                    <c:forEach items="${roles}" var="role">
						<option value="${role.id}">${role.name}</option>
					</c:forEach>
                 </select>
             </div>
         </div>
	</div>
	<div class="modal-footer">
	  	<button type="button" class="btn btn-default btn-sm" data-dismiss="modal">关闭</button>
	  	<button type="submit" class="btn btn-inverse btn-sm">保存</button>
	</div>
	</form:form>
</body>
</html>