package com.LinYuda.www.servlet;

import com.LinYuda.www.po.ProductMenu;
import com.LinYuda.www.service.ProductMenuService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 厨师添加新商品的servlet
 */
@WebServlet(name = "AddNewMenuServlet", urlPatterns = "/AddNewMenuServlet")
public class AddNewMenuServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("utf-8");

        String mealNameString = request.getParameter("mealName");
        String priceString = request.getParameter("price");
        String cookNoString = request.getParameter("cookNo");
        String mealTypeString = request.getParameter("mealType");
        String amountString = request.getParameter("amount");
        String windowNoString = request.getParameter("windowNo");

        if (mealNameString != null && priceString != null && cookNoString != null && mealTypeString != null && amountString != null && windowNoString != null) {
            //输入的信息充足
            String mealName = mealNameString;
            double price = Double.parseDouble(priceString);
            long cookNo = Long.parseLong(cookNoString);
            String mealType = ProductMenuService.getTypeName(Integer.parseInt(mealTypeString));
            long amount = Long.parseLong(amountString);
            int windowNo = Integer.parseInt(windowNoString);
            ProductMenu productMenu = new ProductMenu(mealName, price, cookNo, mealType, amount, windowNo);
            ProductMenuService productMenuService = new ProductMenuService();
            boolean result = productMenuService.addNewMenu(productMenu);
            if (result) {
                //添加成功
                response.sendRedirect("CookView/addMenuSuccess.jsp");
            } else {
                //添加失败
                response.sendRedirect("CookView/addMenuFail.jsp");
            }
        } else {
            //输入的信息有空
            response.sendRedirect("CookView/addMenuFail.jsp");
        }


    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
