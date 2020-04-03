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
				Accounts Information
			</div>
		</div>

	
	<a href="${pageContext.request.contextPath}/">Back to Home Page</a>
		
			<div id="container">
			
				<input type="button" value="Add Account" onclick="window.location.href='addAccountForm?customerCode=${customerCode}'; return false;" class="add-button" />
			
				<div id="content">					
					
					<table>
						<tr>
							<th>Account Number</th>
							<th>Balance</th>
							<th>Action</th>
						</tr>
		
						<!-- loop over and print our customers -->
						<c:forEach var="tempAccount" items="${allAccounts}">
							

							<!-- construct an "update" link with customer id -->
							<c:url var="updateLink" value="/updateAccountForm">
								<c:param name="accountNumber" value="${tempAccount.id}" />
							</c:url>					
		
							<!-- construct a "delete" link with customer id -->
							<c:url var="deleteLink" value="/deleteAccount">
								<c:param name="accountNumber" value="${tempAccount.id}" />
							</c:url>					
							
							<tr>
								<td>${tempAccount.id}</td>
								<td>${tempAccount.balance}</td>
								
								<td>
									<!-- display the update link -->
									<a href="${updateLink}">Update</a>
									|
									<a href="${deleteLink}"
									   onclick="if (!(confirm('Are you sure you want to delete this account?'))) return false">Delete</a>
								</td>
								
							</tr>
						
						</c:forEach>
								
					</table>
					<c:out value="${noAccount}"/>
						
				</div>
			
			</div>

		
		
</body>

</html>