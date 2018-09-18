<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<body>
	
	<form:form action="processForm" modelAttribute="loginData">
	
		Login: <form:input placeholder="login"  path="login" />
		
		<br><br>
	
		Password: <form:password placeholder="password" path="password" />
		
		<br/><br/>
	
		<input type="submit" value="Submit" />
	
	</form:form>
	
	<br/>
	<a href="loginPage/about"> about us</a> 
</body>
</html>
