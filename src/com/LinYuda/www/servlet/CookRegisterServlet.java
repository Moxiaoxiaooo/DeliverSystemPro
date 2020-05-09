package com.LinYuda.www.servlet;

import com.LinYuda.www.po.Cook;
import com.LinYuda.www.po.User;
import com.LinYuda.www.service.CookService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 厨师注册的servlet
 * 因为厨师需要输入额外的信息所以有一个独立的Servlet
 */
@WebServlet(name = "CookRegisterServlet", urlPatterns = "/CookRegisterServlet")
public class CookRegisterServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        //获取不带id的user对象和不带id的cook对象
        User user = (User) session.getAttribute("user");
        Cook cook = (Cook) session.getAttribute("cook");

        cook.setSuperiorId(Long.parseLong(request.getParameter("superiorId")));
        cook.setWindowNo(Integer.parseInt(request.getParameter("windowNo")));
        boolean result = new CookService().cookRegister(user, cook);
        if (result) {
            request.getRequestDispatcher("UserView/registerSuccess.jsp").forward(request, response);
        } else {
            request.getRequestDispatcher("UserView/registerFail.jsp").forward(request, response);
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
