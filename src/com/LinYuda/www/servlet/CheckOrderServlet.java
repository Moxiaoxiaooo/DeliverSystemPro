package com.LinYuda.www.servlet;

import com.LinYuda.www.entity.ShoppingCar;
import com.LinYuda.www.po.NormalUser;
import com.LinYuda.www.po.ProductMenu;
import com.LinYuda.www.po.User;
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
 * 普通用户确定下单的servlet
 */
@WebServlet(name = "CheckOrderServlet", urlPatterns = "/CheckOrderServlet")
public class CheckOrderServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        NormalUserService normalUserService = new NormalUserService();
        OrderService orderService = new OrderService();

        User user = (User) request.getSession().getAttribute("user");
        ShoppingCar shoppingCar = (ShoppingCar) request.getSession().getAttribute("shoppingCar");
        NormalUser normalUser = normalUserService.getNormalUserByUserId(user.getId());
        if (shoppingCar.getSelectedMeals().size() == 0) {
            response.sendRedirect("NormalUserView/takeOrderFail.jsp");
            return;
        }

        boolean result = orderService.takeOrder(normalUser, shoppingCar);
        if (result) {
            //下单成功
            ProductMenu[] menus = new ProductMenuService().getAllMenus();
            request.getSession().setAttribute("menus", menus);
            response.sendRedirect("NormalUserView/takeOrderSuccess.jsp");
        } else {
            //下单失败
            response.sendRedirect("NormalUserView/takeOrderFail.jsp");
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
