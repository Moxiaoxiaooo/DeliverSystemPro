<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>登录失败</title>
</head>
<body>
<%
    String error = request.getParameter("error");
    if (error == null) {
        response.sendRedirect("../index.jsp");
    }
    if ("LoginError".equals(error)) {
%>
登录错误，请确定帐号存在或是输入的信息正确<br>
<%
} else if ("AccountNotExist".equals(error)) {
%>
帐号可能不存在或密码错误，请前往注册页面注册<br>
<a href="register.jsp">前往注册页面</a><br>
<%
} else if ("PasswordError".equals(error)) {
%>
密码错误，请重新输入<br>
<%
    }
%>
<a href="../index.jsp">返回上一级页面</a>
</body>
</html>
