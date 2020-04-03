<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
<title>Spring Security Demo - home</title>

<link  rel="stylesheet" href="/css/style.css" >
</head>
<body>
	
	<div id="wrapper">
			<div id="header">
				<h2>CRM - Customer Relationship Management</h2>
				
			</div>
		</div>

	<security:authorize access="hasRole('manager')">
	
		<h4>Role: Manager</h4>
	
	</security:authorize>
	<security:authorize access="hasRole('supervisor')">
	
		<h4>Role: Supervisor</h4>

	</security:authorize>
	<security:authorize access="hasRole('employee')">

		<h4>Role: Employee</h4>

	</security:authorize>
	<form:form action="${pageContext.request.contextPath}/logout" method="POST">
	
	
	<input type="submit" value="Logout" class="add-button" />
	
	</form:form>
		
		<security:authorize access="hasRole('manager')">
			<div id="container">
			
				<div id="content">
				
					<!-- put new button: Add Customer -->
				
					<input type="button" value="Add Customer"
						   onclick="window.location.href='addCustomerForm'; return false;"
						   class="add-button"
					/>
				
					<!--  add our html table here -->
				
					<table>
						<tr>
							<th>Customer Code</th>
							<th>Name</th>
							<th>Tier</th>
							<th>Action</th>
						</tr>
		
						<!-- loop over and print our customers -->
						<c:forEach var="tempCustomer" items="${allCustomers}">
							
							<!--  Construct a account link that review accounts of a customer-->
							<c:url var="accountsLink" value="/accounts">
								<c:param name="customerCode" value="${tempCustomer.customerCode}" />
							</c:url>
							<!-- construct an "update" link with customer id -->
							<c:url var="updateLink" value="/updateCustomerForm">
								<c:param name="customerCode" value="${tempCustomer.customerCode}" />
							</c:url>					
		
							<!-- construct a "delete" link with customer id -->
							<c:url var="deleteLink" value="/deleteCustomer">
								<c:param name="customerCode" value="${tempCustomer.customerCode}" />
							</c:url>					
							
							<tr>
								<td><a href="${accountsLink}">${tempCustomer.customerCode}</a></td>
								<td><a href="${accountsLink}">${tempCustomer.name}</a></td>
								<td> ${tempCustomer.tier} </td>
								
								<td>
									<!-- display the update link -->
									<a href="${updateLink}">Update</a>
									|
									<a href="${deleteLink}"
									   onclick="if (!(confirm('Are you sure you want to delete this customer?'))) return false">Delete</a>
								</td>
								
							</tr>
						
						</c:forEach>
								
					</table>
						
				</div>
			
			</div>
		</security:authorize>
		<security:authorize access="hasRole('supervisor')">
			<div id="container">
			
				<div id="content">
				
					<!-- put new button: Add Customer -->
				
					<input type="button" value="Add Customer"
						   onclick="window.location.href='addCustomerForm'; return false;"
						   class="add-button"
					/>
				
					<!--  add our html table here -->
				
					<table>
						<tr>
							<th>Customer Code</th>
							<th>Name</th>
							<th>Tier</th>
							<th>Action</th>
						</tr>
		
						<!-- loop over and print our customers -->
						<c:forEach var="tempCustomer" items="${tier2And3Customers}">
						
							<!--  Construct a account link that review accounts of a customer-->
							<c:url var="accountsLink" value="/accounts">
								<c:param name="customerCode" value="${tempCustomer.customerCode}" />
							</c:url>
						
							<!-- construct an "update" link with customer id -->
							<c:url var="updateLink" value="/updateCustomerForm">
								<c:param name="customerCode" value="${tempCustomer.customerCode}" />
							</c:url>					
		
							<!-- construct an "delete" link with customer id -->
							<c:url var="deleteLink" value="/deleteCustomer">
								<c:param name="customerCode" value="${tempCustomer.customerCode}" />
							</c:url>					
							
							<tr>
								<td><a href="${accountsLink}">${tempCustomer.customerCode}</a></td>
								<td><a href="${accountsLink}">${tempCustomer.name}</a></td>
								<td> ${tempCustomer.tier} </td>
								
								<td>
									<!-- display the update link -->
									<a href="${updateLink}">Update</a>
									|
									<a href="${deleteLink}"
									   onclick="if (!(confirm('Are you sure you want to delete this customer?'))) return false">Delete</a>
								</td>
								
							</tr>
						
						</c:forEach>
								
					</table>
						
				</div>
			
			</div>
		</security:authorize>
		<security:authorize access="hasRole('employee')">
			<div id="container">
			
				<div id="content">
				
					<!-- put new button: Add Customer -->
				
					<input type="button" value="Add Customer"
						   onclick="window.location.href='addCustomerForm'; return false;"
						   class="add-button"
					/>
				
					<!--  add our html table here -->
				
					<table>
						<tr>
							<th>Customer Code</th>
							<th>Name</th>
							<th>Tier</th>
							<th>Action</th>
						</tr>
		
						<!-- loop over and print our customers -->
						<c:forEach var="tempCustomer" items="${tier3Customers}">
							
							<!--  Construct a account link that review accounts of a customer-->
							<c:url var="accountsLink" value="/accounts">
								<c:param name="customerCode" value="${tempCustomer.customerCode}" />
							</c:url>
						
							<!-- construct an "update" link with customer id -->
							<c:url var="updateLink" value="/updateCustomerForm">
								<c:param name="customerCode" value="${tempCustomer.customerCode}" />
							</c:url>					
		
							<!-- construct an "delete" link with customer id -->
							<c:url var="deleteLink" value="/deleteCustomer">
								<c:param name="customerCode" value="${tempCustomer.customerCode}" />
							</c:url>					
							
							<tr>
								<td><a href="${accountsLink}">${tempCustomer.customerCode}</a></td>
								<td><a href="${accountsLink}">${tempCustomer.name}</a></td>
								<td> ${tempCustomer.tier} </td>
								
								<td>
									<!-- display the update link -->
									<a href="${updateLink}">Update</a>
									|
									<a href="${deleteLink}"
									   onclick="if (!(confirm('Are you sure you want to delete this customer?'))) return false">Delete</a>
								</td>
								
							</tr>
						
						</c:forEach>
								
					</table>
						
				</div>
			
			</div>
		</security:authorize>

</body>

</html>