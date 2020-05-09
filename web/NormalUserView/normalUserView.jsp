<%@ page import="com.LinYuda.www.po.User" %>
<%@ page import="com.LinYuda.www.po.ProductMenu" %>
<%@ page import="com.LinYuda.www.service.ProductMenuService" %>
<%@ page import="com.LinYuda.www.po.NormalUser" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>欢迎食用！</title>
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

<!--建立各种操作按钮-->

<form action="changePassword.jsp" method="post">
    <input type="submit" value="修改密码"/>
</form>

<!--查看历史订单按钮-->
<form action="../CheckHistoryOrdersServlet" method="post">
    <input type="submit" value="我的订单"/>
</form>

<!--查看购物车按钮-->
<form action="checkShoppingCar.jsp" method="post">
    <input type="submit" value="查看购物车"/>
</form>

<form action="../ClassifyMenuServlet" method="get">
    <select name="mealType">
        <option value="0" selected>全部</option>
        <option value="1">粤菜</option>
        <option value="2">川菜</option>
        <option value="3">湘菜</option>
    </select>
    <input type="submit" value="分类"/>
</form>

<form action="../SearchMenuServlet" method="get">
    <input type="text" name="searchName"/>
    <input type="submit" value="搜索"/>
</form>

<!--添加至购物车按钮-->
<form action="../AddToShoppingCarServlet" method="post">
    <%
        ProductMenu[] menus = null;
        //设定添加购物车状态
        session.setAttribute("addShoppingCarStatus", 1);
        menus = (ProductMenu[]) session.getAttribute("menus");

        for (int i = 0; i < menus.length; i++) {
            if (menus[i].getAmount() != 0) {
    %>
    <input type="checkbox" value="<%=menus[i].getId()%>"
           name="selectedMenus"/>
    <%
        }
    %>
    <%=menus[i].getMealName() + "     价格：" + menus[i].getPrice() + "     剩余数量：" + menus[i].getAmount() + "   菜系：" + menus[i].getMealType()  %>
    <br>
    <%
        }
    %>
    <input type="submit" value="添加至购物车"/>
</form>

</body>
</html>
