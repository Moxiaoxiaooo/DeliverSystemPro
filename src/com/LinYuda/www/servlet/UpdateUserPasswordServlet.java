package com.LinYuda.www.servlet;

import com.LinYuda.www.po.User;
import com.LinYuda.www.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 更新用户密码的Servlet
 * 会根据用户的等级分别跳转
 */
@WebServlet(name = "UpdateUserPasswordServlet", urlPatterns = "/UpdateUserPasswordServlet")
public class UpdateUserPasswordServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String newPassword = request.getParameter("newPassword");
        User user = (User) request.getSession().getAttribute("user");
        UserService userService = new UserService();
        if (newPassword == null || "".equals(newPassword)) {
            //修改密码失败：没设置密码或密码为空白
            response.sendRedirect("NormalUserView/changePasswordFail.jsp");
            return;
        }
        boolean result = userService.updateUserPassword(user, newPassword);
        if (result) {
            //修改成功
            switch (user.getPermissionLevel()) {
                case 1:
                case 2: {
                    response.sendRedirect("NormalUserView/changePasswordSuccess.jsp");
                    break;
                }
                case 3: {
                    response.sendRedirect("CookView/changePasswordSuccess.jsp");
                    break;
                }
                case 4:
                case 5: {
                    break;
                }
            }
        } else {
            //修改失败
            switch (user.getPermissionLevel()) {
                case 1:
                case 2: {
                    response.sendRedirect("NormalUserView/changePasswordFail.jsp");
                    break;
                }
                case 3: {
                    response.sendRedirect("CookView/changePasswordFail.jsp");
                    break;
                }
                case 4:
                case 5: {
                    break;
                }

            }
        }


    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
