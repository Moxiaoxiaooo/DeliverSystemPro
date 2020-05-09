package com.LinYuda.www.servlet;

import com.LinYuda.www.po.Cook;
import com.LinYuda.www.po.ProductMenu;
import com.LinYuda.www.service.ProductMenuService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 更新商品单个属性的Servlet
 */
@WebServlet(name = "UpdateMenuServlet", urlPatterns = "/UpdateMenuServlet")
public class UpdateMenuServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String mealNameString = request.getParameter("mealName");
        String priceString = request.getParameter("price");
        String cookNoString = request.getParameter("cookNo");
        String mealTypeString = request.getParameter("mealType");
        String amountString = request.getParameter("mealType");
        String windowNoString = request.getParameter("windowNo");
        Cook cook = (Cook) request.getSession().getAttribute("cook");
        ProductMenu selectedMenu = (ProductMenu) request.getSession().getAttribute("selectedMenu");
        if (cook == null) {
            response.sendRedirect("../index.jsp");
            return;
        }
        ProductMenuService productMenuService = new ProductMenuService();
        boolean result = false;
        if (mealNameString != null) {
            String mealName = mealNameString;
            productMenuService.updateMenuMealNameByMenuId(selectedMenu.getId(), mealName);
        } else if (priceString != null) {
            double price = Double.parseDouble(priceString);
            result = productMenuService.updateMenuPriceByMenuId(selectedMenu.getId(), price);
        } else if (cookNoString != null) {
            long cookNo = Long.parseLong(cookNoString);
            result = productMenuService.updateMenuCookNoByMenuId(selectedMenu.getId(), cookNo);
        } else if (mealTypeString != null) {
            String mealType = ProductMenuService.getTypeName(Integer.parseInt(mealTypeString));
            result = productMenuService.updateMenuMealTypeByMenuId(selectedMenu.getId(), mealType);
        } else if (amountString != null) {
            long amount = Long.parseLong(amountString);
            result = productMenuService.updateMenuAmountByMenuId(selectedMenu.getId(), amount);
        } else if (windowNoString != null) {
            int windowNo = Integer.parseInt(windowNoString);
            result = productMenuService.updateMenuWindowNoByMenuId(selectedMenu.getId(), windowNo);
        }
        if (result) {
            //修改成功
            response.sendRedirect("CookView/updateMenuSuccess.jsp");
        } else {
            //修改失败
            response.sendRedirect("CookView/updateMenuFail.jsp");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
