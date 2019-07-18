<%@ page language="java" contentType="text/html;charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/common/tag.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="expires" content="0" />
<meta http-equiv="Content-Type" content="text/html;charset=utf-8">
<!-- ================== BEGIN PAGE LEVEL JS ================== -->
<link href="${rootUrl}assets/plugins/jstree/dist/themes/default/style.min.css" rel="stylesheet" />
<!-- ================== END PAGE LEVEL JS ================== -->

<!-- ================== BEGIN PAGE LEVEL JS ================== -->
<script src="${rootUrl}assets/plugins/jstree/dist/jstree.min.js"></script>
<!-- ================== END PAGE LEVEL JS ================== -->
<script>
	$(document).ready(function() {
		var treeData = ${treeJson};
		$('#jstree-checkable').jstree({
	        'plugins': ["wholerow", "checkbox", "types"],
	        'core': {
	            "themes": {
	                "responsive": false
	            },    
	            'data': treeData
	        },
	        "types": {
	        	'default': {
	                'icon': 'fa fa-folder text-warning fa-lg'
	            },
	            'file': {
	                'icon': 'fa fa-file text-inverse fa-lg'
	            },
	            'link': {
	            	'icon': 'fa fa-link fa-lg text-primary'
	            }
	        }
	    });
		
		$('#jstree-checkable').jstree(true).get_all_checked = function(full) {
		    var tmp=new Array;
		    for(var i in this._model.data) {
		        if((this.is_undetermined(i) || this.is_checked(i)) && !(i === '#')) {
		        	tmp.push(full?this._model.data[i]:i);
		        }
		    }
		    return tmp;
		};
		
		$("#submit").click(function() {
			$("#resourceIds").val($('#jstree-checkable').jstree(true).get_all_checked(false));
			$.ajax({
				url: '${rootUrl }app/acl/role/configRole',
				type: 'POST',
				data: {
					'roleId': $("#roleId").val(),
					'resourceIds': $("#resourceIds").val() 
				},
				beforeSend: function(xhr) {
					if($("#resourceIds").val().length == 0 || $("#resourceIds").val() == null || $("#resourceIds").val() == '') {
						$.showWarnMsg("请至少选中一个资源");
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
						$.showMsg(true,new Base64().decode(xhr.getResponseHeader($.Constans.RESPONSE_HEADER_NOTE)));
					}
				},
				error: function(xhr, status, error) {
					$.showWarnMsg("系统异常，请稍后重试！");
				}
			});
		});
	});
</script>
</head>
<body>
	<div class="modal-header">
	  	<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	  	<h4 class="modal-title">资源配置</h4>
	</div>
	<div class="modal-body">
		<input type="hidden" id="roleId" value="${roleId }" />
        <input type="hidden" id="resourceIds" value="" />
        <div class="panel-body">
		   <div id="jstree-checkable"></div>
		</div>
	</div>
	<div class="modal-footer">
	  	<button type="button" class="btn btn-default btn-sm" data-dismiss="modal">关闭</button>
	  	<button id="submit" type="button" class="btn btn-inverse btn-sm">保存</button>
	</div>
</body>
</html>