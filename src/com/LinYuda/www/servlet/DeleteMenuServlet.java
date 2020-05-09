package com.LinYuda.www.servlet;

import com.LinYuda.www.service.ProductMenuService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 厨师删除商品的servlet
 */
@WebServlet(name = "DeleteMenuServlet", urlPatterns = "/DeleteMenuServlet")
public class DeleteMenuServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String selectedMenuIdString = request.getParameter("selectedMenuId");
        long selectedMenuId = Long.parseLong(selectedMenuIdString);
        ProductMenuService productMenuService = new ProductMenuService();
        if (selectedMenuIdString != null) {
            boolean result = productMenuService.deleteMenuByMenuId(selectedMenuId);
            if (result) {
                //删除成功
                response.sendRedirect("CookView/deleteMenuSuccess.jsp");
            } else {
                //删除失败
                response.sendRedirect("CookView/deleteMenuFail.jsp?error=1");
            }
        } else {
            //查询不到
            response.sendRedirect("CookView/deleteMenuFail.jsp?error=2");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
