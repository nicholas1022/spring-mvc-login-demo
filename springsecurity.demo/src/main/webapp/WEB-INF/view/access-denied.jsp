<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
<title>Access Denied</title>
</head>

<body>
	<security:authorize access="hasRole('supervisor')">
	
		<h4>You are not allowed to access to tier 1 customer.</h4>

	</security:authorize>
	<security:authorize access="hasRole('employee')">

		<h4>You are not allowed to access to tier 1 and 2 customer.</h4>

	</security:authorize>

<a href="${pageContext.request.contextPath}/">Back to Home Page</a>

</body>
</html>