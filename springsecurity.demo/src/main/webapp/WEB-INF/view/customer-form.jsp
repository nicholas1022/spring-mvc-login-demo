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
			<h2>CRM - Customer Relationship Management</h2>
		</div>
	</div>

	<div id="container">
		<h3>Save Customer</h3>
			
			<form:form action="saveCustomer" modelAttribute="customer" method="POST">
	
				<!-- need to associate this data with customer id -->
				<form:hidden path="customerCode" />
						
					<table>
						<tbody>
						
							<tr>
								<td><label>Name:</label></td>
								<td><form:input path="name" pattern="[A-Za-z]+" title="Only letter is allowed" required="true" /></td>
							</tr>
		
							<tr>
								<td><label>Tier:</label></td>
								<td>
									<security:authorize access="hasRole('manager')">
										<form:input path="tier" type="number" min="1" max="3" title="Only Tier 1 - 3 is allowed" required="true"/>
									</security:authorize>
									
									<security:authorize access="hasRole('supervisor')">
										<form:input path="tier" type="number" min="2" max="3" title="Only Tier 2 - 3 is allowed" required="true"/>
									</security:authorize>
									
									<security:authorize access="hasRole('employee')">
										<form:input path="tier" type="number" min="3" max="3" title="Only Tier 3 is allowed" required="true"/>
									</security:authorize>
									
								</td>
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










