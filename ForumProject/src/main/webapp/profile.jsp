<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<html>
<%@ include file="include/head.htm" %>
<body>
<%@ include file="include/menu.htm" %>
<p> ${user} profile!</p>
<p> name - ${user.firstName}</p>
<p> last name - ${user.lastName}</p>
<p> email - ${user.email}</p>
</body>
</html>
