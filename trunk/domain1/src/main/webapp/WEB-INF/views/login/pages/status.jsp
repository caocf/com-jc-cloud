<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%if(request.getAttribute("userName")!=null){%>
欢迎您:<%=request.getAttribute("userName")%>
<%}else{ %>
<h1><a href="http://member.server.com/login">请登录</a></h1>
<%} %>
