package com.LinYuda.www.servlet;

import com.LinYuda.www.po.NormalUser;
import com.LinYuda.www.po.Order;
import com.LinYuda.www.po.ProductMenu;
import com.LinYuda.www.po.User;
import com.LinYuda.www.entity.FullOrder;
import com.LinYuda.www.service.NormalUserService;
import com.LinYuda.www.service.OrderService;
import com.LinYuda.www.service.ProductMenuService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 用户查看自己的历史订单的Servlet
 */
@WebServlet(name = "CheckHistoryOrdersServlet", urlPatterns = "/CheckHistoryOrdersServlet")
public class CheckHistoryOrdersServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        User user = (User) request.getSession().getAttribute("user");

        OrderService orderService = new OrderService();
        ProductMenuService productMenuService = new ProductMenuService();
        NormalUserService normalUserService = new NormalUserService();

        Order[] orders = orderService.getHistoryOrders(user);
        FullOrder[] fullOrders = new FullOrder[orders.length];
        ProductMenu productMenu = null;
        NormalUser normalUser = null;
        for (int i = 0; i < orders.length; i++) {
            productMenu = productMenuService.getMenuById(orders[i].getOrderMealNo());
            normalUser = normalUserService.getNormalUserByUserId(orders[i].getOrderPersonId());
            //获取对应对象并设置完整订单对象的属性
            fullOrders[i] = new FullOrder(orders[i].getId(), productMenu.getMealName(), orders[i].getOrderTime(), normalUser.getLocation(), normalUser.getUserName(), orders[i].getStatus());
        }

        request.getSession().setAttribute("historyOrders", fullOrders);


        request.getRequestDispatcher("NormalUserView/checkHistoryOrders.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
