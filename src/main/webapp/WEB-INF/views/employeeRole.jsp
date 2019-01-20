<%@page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="jstlCore" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="springForm" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="jstlFormat" uri="http://java.sun.com/jsp/jstl/fmt"%> 
<%@taglib prefix="jstlFn" uri="http://java.sun.com/jsp/jstl/functions"%> 


<jsp:include page="/header.jsp" />


<link rel="stylesheet" type="text/css" href="${contextRoot}/assets/css/jquery/jquery-ui.css">
<link rel="stylesheet" type="text/css" href="${contextRoot}/assets/css/tableSorter/tablesorter-theme-ui.css">
<link rel="stylesheet" type="text/css" href="${contextRoot}/assets/css/pNotify/pnotify-custom.css">
<link rel="stylesheet" type="text/css" href="${contextRoot}/assets/css/pNotify/animate.css">


<div style="margin-bottom:1em;">
	<a href='<jstlCore:url value="/EmployeeRole/showAddRole.abhi"/>' class="auto-button" data-icon="ui-icon-plusthick">Add Employee Role</a>
</div>


<table class="tablesorter">
<thead>
	<tr>
		<th class="alignCenter">Sl No.</th>
		<th class="alignCenter">Role Name</th>
		<th class="alignCenter">CreatedDate</th>
		<th class="alignCenter">UpdatedDate</th>
		<th class="alignCenter">Archive</th>
		<th class="alignCenter">Action</th>
	</tr>
</thead>
<tbody>
	<jstlCore:choose>
		<jstlCore:when test="${jstlFn:length(employeeRoles) > 0}">
			<jstlCore:forEach items="${employeeRoles}" var="role" varStatus="loopStatus">
				<tr>
					<td class="alignCenter"><jstlCore:out value="${loopStatus.index+1}"/></td>
					<td class="alignCenter"><jstlCore:out value="${role.roleName}"/></td>
					<td class="alignCenter"><jstlFormat:formatDate value="${role.recordCreated}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
					<jstlFormat:formatDate value="${role.recordUpdated}" pattern="yyyy-MM-dd HH:mm:ss" var="updatedDate" />
					<td class="alignCenter"><jstlCore:out value="${not empty updatedDate ? updatedDate : 'N/A'}"/></td>
					<td class="alignCenter"><jstlCore:out value="${role.archive eq 0 ? 'NO' : 'YES'}"/></td>
					<td class="alignCenter">
						<a href='<jstlCore:url value="/EmployeeRole/showEditRole.abhi?roleId=${role.roleId}"/>' class="auto-button" data-icon="ui-icon-custom-edit">Edit</a>
						<a href='<jstlCore:url value="/EmployeeRole/deleteRole.abhi?roleId=${role.roleId}"/>' class="auto-button deleteRole" data-icon="ui-icon-custom-delete">Delete</a>
					</td>
				</tr>
		  	</jstlCore:forEach>
		</jstlCore:when>
		<jstlCore:otherwise>
			<tr><td colspan="3" class="alignCenter error_message">No Employee Role Found</td></tr>
		</jstlCore:otherwise>
	</jstlCore:choose>
</tbody>
</table>

<!-- Dialog for delete role -->
<div id="dialog-confirm" title="Delete Employee Role">
	<div style="padding-top:15px;float:left;">
		<img alt="alert" height="80px" width="80px" src="${contextRoot}/assets/css/images/alert.png">
	</div>
	<div style="display:flex;">
		<h2 style="margin-left:1.5em; margin-top:1em;">This role will be permanently deleted and cannot be recovered.<p>Are you sure ?</p></h2>
	</div>
</div>


<script type="text/javascript" src="${contextRoot}/assets/plugins/jquery/jquery.js"></script>
<script type="text/javascript" src="${contextRoot}/assets/plugins/jquery/jquery-ui.js"></script>
<script type="text/javascript" src="${contextRoot}/assets/plugins/tableSorter/tablesorter.js"></script>
<script type="text/javascript" src="${contextRoot}/assets/plugins/tableSorter/widget-uitheme.js"></script>
<script type="text/javascript" src="${contextRoot}/assets/plugins/pNotify/pnotify-custom.js"></script>
<script type="text/javascript" src="${contextRoot}/assets/js/employeeRole.js"></script>
<script type="text/javascript">
$(function() {
	var message = '${message}';

	if (message != "") {
		new PNotify({
			type : '${jstlFn:toLowerCase(messageType)}', // type : 'Success' not correct so type : 'success'
			styling : "jqueryui",
			title : '${messageType}',
			text : message
		});
	}
})
</script>


<jsp:include page="/footer.jsp" />