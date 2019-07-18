<%@ page language="java" contentType="text/html;charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/common/tag.jsp"%>
	
<tr>
	<td>${data.loginName }</td>
	<td>${data.name }</td>
	<td>${data.status.value }</td>
	<td>${data.role.name }</td>
	<td>
	<c:choose>
		<c:when test="${data.id eq 'default' || data.loginName eq 'admin' }">
			系统内置用户
		</c:when>
		<c:otherwise>
			<a class="update_op" href="${rootUrl }app/acl/user/updateUserRole/${data.id}">修改角色</a>
			<c:if test="${data.status eq 'INVALID' }">
			<a class="status_op" href="${rootUrl }app/acl/user/status/${data.id}/NORMAL">启用</a>
			</c:if>
			<c:if test="${data.status eq 'NORMAL' }">
			<a class="status_op" href="${rootUrl }app/acl/user/status/${data.id}/INVALID">禁用</a>
			</c:if>
		</c:otherwise>
	</c:choose>
	</td>
</tr>