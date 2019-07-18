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
	$("#submit").click(function (){
		$.ajax({
			url: '${rootUrl }app/acl/user/updateUserRole',
			type: 'POST',
			data: {
				'roleId': $("#roleId").val(),
				'userId': $("#userId").val() 
			},
			beforeSend: function(xhr) {
				if($("#roleId").val().length == 0 || $("#roleId").val() == null || $("#roleId").val() == '') {
					$.showWarnMsg("请选择角色");
					$._hideModal();
					return false;
				}
			},
			complete: function(xhr) {
				$._hideModal();
			},
			success: function(responseText, status, xhr) {
				if(xhr.getResponseHeader($.Constans.RESPONSE_HEADER_ERROR)){
					$.showWarnMsg(responseText.msg);
				}else if(xhr.getResponseHeader($.Constans.RESPONSE_HEADER_NOTE)){
					$._handleTableData(responseText, "update");
					$.showMsg(true,new Base64().decode(xhr.getResponseHeader($.Constans.RESPONSE_HEADER_NOTE)));
				}
			},
			error: function(xhr, status, error) {
				$.showWarnMsg("系统异常，请稍后重试！");
			}
		});
		return false;
	});
});
</script>
</head>
<body>
	<div class="modal-header">
	  	<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	  	<h4 class="modal-title">编辑资源</h4>
	</div>
	<div class="modal-body form-horizontal">
         <input type="hidden" id="userId" name="userId" value="${user.id }"/>
		 <div class="form-group">
             <label class="col-md-3 control-label">账号</label>
             <div class="col-md-7">
                <input type="text" class="form-control" placeholder="账号" value="${user.loginName }" />
             </div>
         </div>
         <div class="form-group">
             <label class="col-md-3 control-label">角色</label>
             <div class="col-md-7">
				 <select id="roleId" name="roleId" class="form-control selectpicker" data-size="10" data-live-search="true" data-style="btn-inverse">
                    <option value="">请选择...</option>
                    <c:forEach items="${roles}" var="role">
						<option value="${role.id}" ${role.id == user.role.id?'selected':''}>${role.name}</option>
					</c:forEach>
                 </select>
             </div>
         </div>
	</div>
	<div class="modal-footer">
	  	<button type="button" class="btn btn-default btn-sm" data-dismiss="modal">关闭</button>
	  	<button id="submit" type="button" class="btn btn-inverse btn-sm">保存</button>
	</div>
</body>
</html>