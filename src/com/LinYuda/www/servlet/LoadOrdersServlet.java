package com.LinYuda.www.servlet;

import com.LinYuda.www.entity.FullOrder;
import com.LinYuda.www.po.Cook;
import com.LinYuda.www.po.NormalUser;
import com.LinYuda.www.po.Order;
import com.LinYuda.www.po.ProductMenu;
import com.LinYuda.www.service.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 厨师端加载主界面，也就是加载菜单的Servlet
 * 返回主菜单前最好重新载入该servlet
 */
@WebServlet(name = "LoadOrdersServlet", urlPatterns = "/LoadOrdersServlet")
public class LoadOrdersServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cook cook = (Cook) request.getSession().getAttribute("cook");

        ProductMenuService productMenuService = new ProductMenuService();
        NormalUserService normalUserService = new NormalUserService();
        CookService cookService = new CookService();

        ProductMenu productMenu = null;
        NormalUser normalUser = null;
        Order[] orders = cookService.getCookOrderByCookId(cook.getId());
        FullOrder[] fullOrders = new FullOrder[orders.length];
        for (int i = 0; i < orders.length; i++) {
            productMenu = productMenuService.getMenuById(orders[i].getOrderMealNo());
            normalUser = normalUserService.getNormalUserByUserId(orders[i].getOrderPersonId());
            fullOrders[i] = new FullOrder(orders[i].getId(), productMenu.getMealName(), orders[i].getOrderTime(), normalUser.getLocation(), normalUser.getUserName(), orders[i].getStatus());
        }
        request.getSession().setAttribute("orders", fullOrders);
        response.sendRedirect("CookView/cookView.jsp");

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
