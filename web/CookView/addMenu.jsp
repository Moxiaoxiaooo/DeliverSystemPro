<%@ page import="com.LinYuda.www.po.User" %>
<%@ page import="com.LinYuda.www.po.Cook" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>添加商品</title>
</head>
<body>
<%
    //校验用户是否有登录，如果没有，跳转到登录页面
    User user = (User) session.getAttribute("user");
    if (user == null) {
        response.sendRedirect("../index.jsp");
    }
    if (user.getId() == -1 || user.getPermissionLevel() == -1) {
        response.sendRedirect("../index.jsp");
    }
    Cook cook = (Cook) session.getAttribute("cook");
    if (cook == null) {
        response.sendRedirect("../index.jsp");
    }
%>
<form action="../AddNewMenuServlet" method="post">
    名字：<input type="text" name="mealName"/>
    <br>

    价格：<input type="text" name="price"/>
    <br>

    厨师编号：<input type="text" name="cookNo"/>
    <br>

    商品类型：
    <!--value对应ProductMenuService中的常量-->
    <select name="mealType">
        <option value="1" selected>粤菜</option>
        <option value="2">川菜</option>
        <option value="3">湘菜</option>
    </select>
    <br>

    数量：<input type="text" name="amount"/>
    <br>

    窗口编号：<input type="text" name="windowNo"/>
    <br>

    <input type="submit" value="提交"/>
</form>
<br>
<a href="../LoadOrdersServlet">返回主界面</a>

</body>
</html>
