<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>


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

	<security:authorize access="hasRole('supervisor')">
	
		<h4>You are not allowed to delete tier 1 customer.</h4>

	</security:authorize>
	<security:authorize access="hasRole('employee')">

		<h4>You are not allowed to delete tier 1 and 2 customer.</h4>

	</security:authorize>
	
	<a href="${pageContext.request.contextPath}/">Back to Home Page</a>
	
</body>

</html>