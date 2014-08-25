<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>SOA2</title>
</head>
<body>
<%=session.getId() %><br/>
<h1><%=session.getAttribute("name") %></h1>
<%session.setAttribute("jacky", "chen"); %><br/>
<h1><%=session.getAttribute("jacky") %></h1>
</body>
</html>