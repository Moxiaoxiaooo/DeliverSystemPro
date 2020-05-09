<%@ page import="com.LinYuda.www.entity.ShoppingCar" %>
<%@ page import="java.util.List" %>
<%@ page import="com.LinYuda.www.po.ProductMenu" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="com.LinYuda.www.po.User" %>
<%@ page import="com.LinYuda.www.po.NormalUser" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>添加商品成功</title>
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
    NormalUser normalUser = (NormalUser) session.getAttribute("normalUser");
    if (normalUser == null) {
        response.sendRedirect("../index.jsp");
    }
%>

<%
    int status = (Integer) session.getAttribute("addShoppingCarStatus");
    if (status != 1) {
        response.sendRedirect("NormalUserView/normalUserView.jsp");
    } else {
%>
添加商品成功！<br>
现在已有商品：<br>
<%
    ShoppingCar shoppingCar = (ShoppingCar) session.getAttribute("shoppingCar");
    List<ProductMenu> menus = shoppingCar.getSelectedMeals();
    Iterator iterator = menus.iterator();
    while (iterator.hasNext()) {
        ProductMenu temp = (ProductMenu) iterator.next();
%>
<%=temp.getMealName() + "    价格：" + temp.getPrice()%><br>
<%
        }
        session.setAttribute("addShoppingCarStatus", 0);
    }
%>
<a href="NormalUserView/normalUserView.jsp">返回点单页面</a>
</body>
</html>
