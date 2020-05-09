package com.LinYuda.www.servlet;

import com.LinYuda.www.entity.ShoppingCar;
import com.LinYuda.www.service.ShoppingCarService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 从购物车移除商品的servlet
 */
@WebServlet(name = "RemoveFromShoppingServlet", urlPatterns = "/RemoveFromShoppingServlet")
public class RemoveFromShoppingServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ShoppingCar shoppingCar = (ShoppingCar) request.getSession().getAttribute("shoppingCar");
        int id = Integer.parseInt(request.getParameter("mealId"));
        boolean check = new ShoppingCarService().removeFromShoppingCarByMealId(id, shoppingCar);
        if (check) {
            //删除成功
            request.getRequestDispatcher("NormalUserView/removeMealSuccess.jsp").forward(request, response);
            request.getSession().setAttribute("shoppingCar", shoppingCar);
        } else {
            //删除失败
            request.getRequestDispatcher("NormalUserView/removeMealFail.jsp").forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
