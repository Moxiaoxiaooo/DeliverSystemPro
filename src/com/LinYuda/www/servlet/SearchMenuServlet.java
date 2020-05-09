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
 * 通过名字搜索商品的servlet
 * 通过设置属性的方式让normalUserView在获取菜单的时候搜索
 */
@WebServlet(name = "SearchMenuServlet", urlPatterns = "/SearchMenuServlet")
public class SearchMenuServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String searchName = request.getParameter("searchName");
        //设置商品菜单
        ProductMenu[] menus = null;
        //获取是否有搜索名字
        if (searchName != null) {
            //有选择查询名字时：
            menus = new ProductMenuService().getSearchMenu(searchName);
            request.getSession().setAttribute("searchMenuName", null);
            if (menus == null) {
                //查询不到商品
                response.sendRedirect("NormalUserView/searchMenuFail.jsp");
                return;
            }
        }
        request.getSession().setAttribute("menus", menus);
        response.sendRedirect("NormalUserView/normalUserView.jsp");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
