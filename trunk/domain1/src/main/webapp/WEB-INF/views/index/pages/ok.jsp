<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>SOA1</title>
</head>
<body>
Distributed SessionID:<font color='red'><%=session.getId() %></font><br/>
SERVER IDENTITY:<font color='red'>192.168.128.131-1</font><br/>
UserName SSO:<%=request.getAttribute("UserName")%>


<br/>
<a href="/domain1/index/logout">logout</a>
</body>
</html>