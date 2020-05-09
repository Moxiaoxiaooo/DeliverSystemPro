<%@ page import="com.LinYuda.www.po.User" %>
<%@ page import="com.LinYuda.www.po.Cook" %>
<%@ page import="com.LinYuda.www.po.ProductMenu" %>
<%@ page import="com.LinYuda.www.service.ProductMenuService" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
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
    <%
        ProductMenu updatedMenu = (ProductMenu) session.getAttribute("updatedMenu");
    %>
    <title>修改<%=updatedMenu.getMealName()%>
    </title>
</head>
<body>

当前商品状态：<%=updatedMenu%><br><br>

<form action="../UpdateMenuServlet" method="post">
    名字：<input type="text" value="<%=updatedMenu.getMealName()%>" name="mealName"/>
    <input type="submit" value="修改名字"/>
</form>
<br>

<form action="../UpdateMenuServlet" method="post">
    价格：<input type="text" value="<%=updatedMenu.getPrice()%>" name="price"/>
    <input type="submit" value="修改价格"/>
</form>
<br>

<form action="../UpdateMenuServlet" method="post">
    厨师编号：<input type="text" value="<%=updatedMenu.getCookNo()%>" name="cookNo"/>
    <input type="submit" value="修改厨师编号"/>
</form>
<br>

<form action="../UpdateMenuServlet" method="post">
    <!--value对应ProductMenuService中的常量-->
    <select name="mealType">
        <% if ("粤菜".equals(updatedMenu.getMealType())) {%>
        <option value="1" selected>粤菜</option>
        <option value="2">川菜</option>
        <option value="3">湘菜</option>
        <%} else if ("川菜".equals(updatedMenu.getMealType())) {%>
        <option value="1">粤菜</option>
        <option value="2" selected>川菜</option>
        <option value="3">湘菜</option>
        <%} else if ("湘菜".equals(updatedMenu.getMealType())) {%>
        <option value="1">粤菜</option>
        <option value="2">川菜</option>
        <option value="3" selected>湘菜</option>
        <%}%>
    </select>
    <input type="submit" value="修改商品类型"/>
</form>
<br>

<form action="../UpdateMenuServlet" method="post">
    数量：<input type="text" value="<%=updatedMenu.getAmount()%>" name="amount"/>
    <input type="submit" value="修改数量"/>
</form>
<br>

<form action="../UpdateMenuServlet" method="post">
    窗口编号：<input type="text" value="<%=updatedMenu.getWindowNo()%>" name="windowNo"/>
    <input type="submit" value="修改窗口编号"/>
</form>
<br>
<br>
<a href="../DeleteMenuServlet?selectedMenuId=<%=updatedMenu.getId()%>">删除这个商品</a><br>
<a href="../LoadOrdersServlet">返回主界面</a>
</body>
</html>
