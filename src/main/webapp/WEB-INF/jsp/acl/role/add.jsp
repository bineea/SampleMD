<%@ page language="java" contentType="text/html;charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/common/tag.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="expires" content="0" />
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<script>
$(document).ready(function() {
	$('#myForm').ajaxForm({
		type: "post", //提交方式 
		complete: function(xhr) {
			$._hideModal();
		},
		success: function(responseText, status, xhr){
			if(xhr.getResponseHeader($.Constans.RESPONSE_HEADER_ERROR)){
				$.showWarnMsg(responseText.msg);
			}else if(xhr.getResponseHeader($.Constans.RESPONSE_HEADER_NOTE) && '${!isRootMenu}'){
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
	  	<h4 class="modal-title">添加角色</h4>
	</div>
	<form:form modelAttribute="addModel" id="myForm" name="myForm" cssClass="form-horizontal" action="${rootUrl }app/acl/role/addRole" method="post">
	<div class="modal-body">
		 <div class="form-group">
             <label class="col-md-3 control-label">角色名称</label>
             <div class="col-md-7">
                 <input name="name" type="text" class="form-control" placeholder="角色名称" />
             </div>
         </div>
         <div class="form-group">
             <label class="col-md-3 control-label">序号</label>
             <div class="col-md-7">
                 <input name="list" type="text" class="form-control" placeholder="排序号" />
             </div>
         </div>
         <div class="form-group">
             <label class="col-md-3 control-label">备注</label>
             <div class="col-md-7">
                 <textarea name="info" class="form-control" placeholder="备注"></textarea>
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