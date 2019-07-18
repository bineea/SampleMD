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
				$._handleTableData(responseText, "update");
				$('#jstree-default').jstree(true).refresh(); //刷新树
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
	  	<h4 class="modal-title">编辑资源</h4>
	</div>
	<form:form modelAttribute="editModel" id="myForm" name="myForm" cssClass="form-horizontal" action="${rootUrl }app/acl/resource/updateResource" method="post">
	<div class="modal-body">
         <input type="hidden" id="id" name="id" value="${editModel.id }"/>
		 <div class="form-group">
             <label class="col-md-3 control-label">资源名称</label>
             <div class="col-md-7">
                <input name="name" type="text" class="form-control" placeholder="资源名称" value="${editModel.name }" />
             </div>
         </div>
         <div class="form-group">
             <label class="col-md-3 control-label">URL</label>
             <div class="col-md-7">
             	<input type="hidden" id="url" name="url" value="${editModel.url }"/>
                <p class="form-control-static">${editModel.url }</p>
             </div>
         </div>
         <div class="form-group">
             <label class="col-md-3 control-label">请求方式</label>
             <div class="col-md-7">
             	<input type="hidden" id="requestMethod" name="requestMethod" value="${editModel.requestMethod }"/>
                <p class="form-control-static">${editModel.requestMethod }</p>
             </div>
         </div>
         <div class="form-group">
             <label class="col-md-3 control-label">菜单类型</label>
             <div class="col-md-7">
             	<input type="hidden" id="menuType" name="menuType" value="${editModel.menuType }"/>
                <p class="form-control-static">${editModel.menuType.value }</p>
             </div>
         </div>
         <%-- <div class="form-group">
             <label class="col-md-3 control-label">请求方式</label>
             <div class="col-md-7">
                 <select name="requestMethod" class="form-control">
                 	<option value=" ">请选择</option>
					<c:forEach items="${requestMethods}" var="method">
						<option value="${method}" ${method==editModel.requestMethod?'selected':''}>${method}</option>
					</c:forEach>
                 </select>
             </div>
         </div>
         <div class="form-group">
             <label class="col-md-3 control-label">菜单类型</label>
             <div class="col-md-7">
                <form:select path="menuType" class="form-control" >
                	<form:option value=" ">请选择</form:option>
					<form:options items="${menuTypes}" itemLabel="value"/>
				</form:select>
             </div>
         </div> --%>
         <div class="form-group">
             <label class="col-md-3 control-label">序号</label>
             <div class="col-md-7">
                <form:input path="list" type="text" class="form-control" placeholder="排序号" />
             </div>
         </div>
         <div class="form-group">
             <label class="col-md-3 control-label">样式</label>
             <div class="col-md-7">
                <form:input path="logoStyle" type="text" class="form-control" placeholder="样式" />
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