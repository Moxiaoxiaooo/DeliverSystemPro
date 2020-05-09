package com.LinYuda.www.servlet;

import com.LinYuda.www.po.Cook;
import com.LinYuda.www.po.NormalUser;
import com.LinYuda.www.po.User;
import com.LinYuda.www.service.NormalUserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 用户注册的Servlet
 * 但注册的都是不带id的对象
 * id将由数据库自动生成
 */
@WebServlet(name = "RegisterServlet", urlPatterns = "/RegisterServlet")
public class RegisterServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=UTF-8");

        String account = request.getParameter("account");
        String password = request.getParameter("password");
        String userName = request.getParameter("userName");
        String location = request.getParameter("location");
        String contact = request.getParameter("contact");
        int permissionLevel = Integer.parseInt(request.getParameter("permissionLevel"));

        User user = new User(account, password, permissionLevel);


        switch (permissionLevel) {
            case 1:
            case 2: {
                //确定注册
                NormalUser normalUser = new NormalUser(userName, location, contact);
                boolean result = (new NormalUserService().normalUserRegister(user, normalUser));
                if (result) {
                    //注册成功处理
                    request.getRequestDispatcher("UserView/registerSuccess.jsp").forward(request, response);
                } else {
                    //注册失败处理
                    request.getRequestDispatcher("UserView/registerFail.jsp").forward(request, response);
                }
                break;
            }
            case 3: {
                HttpSession session = request.getSession();
                session.setAttribute("user", user);
                Cook cook = new Cook(userName, contact);
                session.setAttribute("cook", cook);
                request.getRequestDispatcher("CookView/registerDetail.jsp").forward(request, response);
                break;
            }
            case 4:
            case 5: {
                response.getWriter().print("还没做呢！");
                break;
            }
        }


    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
