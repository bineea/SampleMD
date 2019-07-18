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
					$.showMsg(false,responseText.msg);
				}else if(xhr.getResponseHeader($.Constans.RESPONSE_HEADER_NOTE)){
					$.showMsg(true,new Base64().decode(xhr.getResponseHeader($.Constans.RESPONSE_HEADER_NOTE)));
				}
			},
			error: function(xhr, status, error) {
				$.showMsg(false,"系统异常，请稍后重试！");
			}
		});
	});
</script>
</head>
<body>
	<div class="modal-header">
	  	<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	  	<h4 class="modal-title">Modify Password</h4>
	</div>
	<form:form modelAttribute="userInfoModel" id="myForm" name="myForm" cssClass="form-horizontal" action="${rootUrl }app/modPasswd" method="post">
	<div class="modal-body">
		 <div class="form-group">
             <label class="col-md-3 control-label">原始密码</label>
             <div class="col-md-7">
                 <input name="oldPasswd" type="password" class="form-control" placeholder="原始密码" />
             </div>
         </div>
         <div class="form-group">
             <label class="col-md-3 control-label">新密码</label>
             <div class="col-md-7">
                 <input name="passwd" type="password" class="form-control" placeholder="新密码" />
             </div>
         </div>
         <div class="form-group">
             <label class="col-md-3 control-label">确认新密码</label>
             <div class="col-md-7">
                 <input name="confirmPw" type="password" class="form-control" placeholder="确认新密码" />
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