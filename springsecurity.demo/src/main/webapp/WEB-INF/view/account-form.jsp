<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>
<html>

<head>
	<title>Save Customer</title>

	<link type="text/css"
		  rel="stylesheet"
		  href="${pageContext.request.contextPath}/resources/css/style.css">

	<link type="text/css"
		  rel="stylesheet"
		  href="${pageContext.request.contextPath}/resources/css/add-customer-style.css">
</head>

<body>
	
	<div id="wrapper">
		<div id="header">
			<h2>CRM - Account Management</h2>
		</div>
	</div>

	<div id="container">
		<h3>Save Account</h3>
		
			<form:form action="saveAccount" modelAttribute="account" method="POST">
	
				<!-- need to associate this data with account id -->
				<form:hidden path="id" />
				<form:hidden path="customerId" />
				
					<table>
						<tbody>
						
							<tr>
								<td><label>Balance:</label></td>
								<td><form:input path="balance" pattern="[0-9]+" title="Only digit is allowed" required="true"/></td>
							</tr>
		
							<tr>
								<td><label></label></td>
								<td><input type="submit" value="Save" class="save" /></td>
							</tr>
		
						
						</tbody>
					</table>
		
			</form:form>
		<div style="clear; both;"></div>
		
		<p>
			<a href="${pageContext.request.contextPath}/">Back to List</a>
		</p>
	
	</div>

</body>

</html>










