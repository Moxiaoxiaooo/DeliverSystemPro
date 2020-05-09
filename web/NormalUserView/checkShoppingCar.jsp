<%@ page import="com.LinYuda.www.entity.ShoppingCar" %>
<%@ page import="com.LinYuda.www.po.ProductMenu" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.List" %>
<%@ page import="com.LinYuda.www.po.User" %>
<%@ page import="com.LinYuda.www.po.NormalUser" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>查看购物车</title>
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

现在已有商品：<br>
<%
    ShoppingCar shoppingCar = (ShoppingCar) session.getAttribute("shoppingCar");
    List<ProductMenu> menus = shoppingCar.getSelectedMeals();
    Iterator iterator = menus.iterator();
    while (iterator.hasNext()) {
        ProductMenu temp = (ProductMenu) iterator.next();
%>

<%=temp.getMealName() + "    价格：" + temp.getPrice() + "        "%><a
        href="../RemoveFromShoppingServlet?mealId=<%=temp.getId()%>">删除选中</a><br>
<%
    }
%>

总数量：<%=shoppingCar.getAmount()%><br>
总价格：<%=shoppingCar.getTotalPrice()%><br>

<form action="../CheckOrderServlet" method="post">
    <input type="submit" value="确定订单"/>
</form>
<a href="normalUserView.jsp">返回商品选择页面</a>
</body>
</html>
