package com.LinYuda.www.servlet;

import com.LinYuda.www.po.Cook;
import com.LinYuda.www.po.NormalUser;
import com.LinYuda.www.po.Order;
import com.LinYuda.www.po.ProductMenu;
import com.LinYuda.www.service.CookService;
import com.LinYuda.www.entity.FullOrder;
import com.LinYuda.www.service.NormalUserService;
import com.LinYuda.www.service.ProductMenuService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 厨师确定所有相关订单的servlet
 */
@WebServlet(name = "CheckAllOrdersServlet", urlPatterns = "/CheckAllOrdersServlet")
public class CheckAllOrdersServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cook cook = (Cook) request.getSession().getAttribute("cook");
        CookService cookService = new CookService();
        Order[] orders = cookService.getCookOrderByCookId(cook.getId());
        FullOrder[] allOrders = new FullOrder[orders.length];
        ProductMenu productMenu = null;
        NormalUser normalUser = null;
        for (int i = 0; i < orders.length; i++) {
            productMenu = new ProductMenuService().getMenuById(orders[i].getOrderMealNo());
            normalUser = new NormalUserService().getNormalUserByUserId(orders[i].getOrderPersonId());
            //获取对应对象并设置完整订单对象的属性
            allOrders[i] = new FullOrder(orders[i].getId(), productMenu.getMealName(), orders[i].getOrderTime(), normalUser.getLocation(), normalUser.getUserName(), orders[i].getStatus());
        }
        request.getSession().setAttribute("allOrders", allOrders);
        response.sendRedirect("CookView/checkOrders.jsp");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
