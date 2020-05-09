<%@ page import="com.LinYuda.www.service.CookService" %>
<%@ page import="com.LinYuda.www.service.ProductMenuService" %>
<%@ page import="com.LinYuda.www.service.NormalUserService" %>
<%@ page import="com.LinYuda.www.po.*" %>
<%@ page import="com.LinYuda.www.entity.FullOrder" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>厨师管理端</title>
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
<form action="changePassword.jsp" method="post">
    <input type="submit" value="修改密码"/>
</form>

<form action="updateMenu.jsp" method="post">
    <input type="submit" value="修改商品属性"/>
</form>

<form action="../CheckAllOrdersServlet" method="post">
    <input type="submit" value="查看所有订单"/>
</form>

<%
    FullOrder[] orders = (FullOrder[]) session.getAttribute("orders");
    for (int i = 0; i < orders.length; i++) {
        if (orders[i].getStatus() == 0) {
%>
<%=orders[i].getMealName() + "   下单时间：" + orders[i].getOrderTime() + "    地址：" + orders[i].getOrderPersonLocation() + "    点单人：" + orders[i].getOrderPersonName()%>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a
        href="../FinishOrderServlet?orderId=<%=orders[i].getOrderId()%>">完成这个订单</a><br>
<%
        }
    }
%>
</body>
</html>
