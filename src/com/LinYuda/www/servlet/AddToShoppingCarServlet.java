package com.LinYuda.www.servlet;

import com.LinYuda.www.entity.ShoppingCar;
import com.LinYuda.www.po.ProductMenu;
import com.LinYuda.www.service.ProductMenuService;
import com.LinYuda.www.service.ShoppingCarService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 普通用户将商品添加至购物车的servlet
 */
@WebServlet(name = "AddToShoppingCarServlet", urlPatterns = "/AddToShoppingCarServlet")
public class AddToShoppingCarServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取状态，检查是否可以添加至购物车
        int status = (Integer) request.getSession().getAttribute("addShoppingCarStatus");
        if (status == 1) {
            //先获取购物车和商品id
            String[] selectedMenusIds = request.getParameterValues("selectedMenus");
            if (selectedMenusIds == null) {
                //没有选种商品
                response.sendRedirect("NormalUserView/addShoppingCarFail.jsp");
                return;
            }
            ShoppingCar shoppingCar = (ShoppingCar) request.getSession().getAttribute("shoppingCar");
            //通过id获取多个商品
            long[] ids = new long[selectedMenusIds.length];
            for (int i = 0; i < ids.length; i++) {
                ids[i] = Long.parseLong(selectedMenusIds[i]);
            }
            //将商品添加至购物车
            ProductMenuService productMenuService = new ProductMenuService();
            ShoppingCarService shoppingCarService = new ShoppingCarService();
            ProductMenu[] productMenus = productMenuService.getMenusById(ids);
            for (int i = 0; i < productMenus.length; i++) {
                shoppingCarService.addInShoppingCar(productMenus[i], shoppingCar);
            }
            request.getRequestDispatcher("NormalUserView/addShoppingCarSuccess.jsp").forward(request, response);
        } else {
            response.sendRedirect("NormalUserView/normalUserView.jsp");
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
