<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>SOA2</title>
</head>
<body>
Distributed SessionID:<font color='red'><%=session.getId() %></font><br/>
SERVER IDENTITY:<font color='red'>192.168.128.147-1</font><br/>
UserName SSO:<%=request.getAttribute("UserName")%>
<br/>
<a href="/domain2/index/logout">logout</a>
</body>
</html>