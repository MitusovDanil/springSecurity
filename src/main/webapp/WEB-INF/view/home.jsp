<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<html>

<head>
    <title>luv2code Company Home Page</title>
</head>

<body>
<h2>luv2code Company Home Page</h2>
<hr>
<p>
    Welcome to the luv2code company home page!
</p>

<p>
    User: <security:authentication property="principal.username" />
<br> <br>
    Role(s) <security:authentication property="principal.authorities" />
</p>

<hr>
<p>
    <security:authorize access="hasRole('MANAGER')">
    <a href="${pageContext.request.contextPath}/leaders"> LeaderSheep meeting</a>
    (Only for manager peeps)
    </security:authorize>
</p>
<p>
    <security:authorize access="hasRole('ADMIN')">
    <a href="${pageContext.request.contextPath}/systems">System stuff</a>
    (Only for admin peeps)
    </security:authorize>
</p>

    <form:form action="${pageContext.request.contextPath}/logout" method="post">
        <input type="submit" value="Logout" />
    </form:form>

</body>

</html>