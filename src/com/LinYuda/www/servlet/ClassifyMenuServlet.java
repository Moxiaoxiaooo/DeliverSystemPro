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
 * 用户分类菜单的servlet
 * 通过设置属性让normalUserView在内部获取菜单的时候进行分类
 */
@WebServlet(name = "ClassifyMenuServlet", urlPatterns = "/ClassifyMenuServlet")
public class ClassifyMenuServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int type = Integer.parseInt(request.getParameter("mealType"));

        //有选择分类时
        ProductMenu[] menus = new ProductMenuService().getTypeMenu(type);
        request.getSession().setAttribute("classifyMenuType", 0);
        request.getSession().setAttribute("menus", menus);
        response.sendRedirect("NormalUserView/normalUserView.jsp");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
