<%@ page language="java" contentType="text/html;charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/common/tag.jsp"%>
	
<tr>
	<td>${data.list }</td>
	<td>${data.name }</td>
	<c:choose>
	<c:when test="${data.system }">
		<td>是</td>
	</c:when>
	<c:otherwise>
		<td>否</td>
	</c:otherwise>
	</c:choose>
	<td>${data.info }</td>
	<td>
	<c:choose>
		<c:when test="${data.system }">
			系统内置角色
		</c:when>
		<c:otherwise>
			<a class="update_op" href="${rootUrl }app/acl/role/updateRole/${data.id}">修改</a>
			<a class="delete_op" href="${rootUrl }app/acl/role/deleteRole/${data.id}">删除</a>
			<a class="config_op" href="${rootUrl }app/acl/role/configRole/${data.id}">配置</a>
		</c:otherwise>
	</c:choose>
	</td>
</tr>