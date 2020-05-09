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
 * 从updateMenu跳转到updateSelectedMenu的一个中间servlet
 * 中间会设置要被设置属性的ProductMenu对象
 */
@WebServlet(name = "ReadyUpdateMenuServlet", urlPatterns = "/ReadyUpdateMenuServlet")
public class ReadyUpdateMenuServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String selectedMenuId = request.getParameter("selectedMenuId");
        long id = Long.parseLong(selectedMenuId);
        ProductMenu selectedMenu = new ProductMenuService().getMenuById(id);
        request.getSession().setAttribute("updatedMenu", selectedMenu);
        response.sendRedirect("CookView/updateSelectedMenu.jsp");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
