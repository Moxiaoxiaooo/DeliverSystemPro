package com.LinYuda.www.servlet;

import com.LinYuda.www.entity.ShoppingCar;
import com.LinYuda.www.po.Cook;
import com.LinYuda.www.po.NormalUser;
import com.LinYuda.www.po.ProductMenu;
import com.LinYuda.www.po.User;
import com.LinYuda.www.service.CookService;
import com.LinYuda.www.service.NormalUserService;
import com.LinYuda.www.service.ProductMenuService;
import com.LinYuda.www.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 用户登录用servlet，校验用户登录，并查询等级
 */
@WebServlet(name = "UserLoginServlet", urlPatterns = "/UserLoginServlet")
public class UserLoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userName = request.getParameter("userName");
        String userPassword = request.getParameter("userPassword");
        UserService userService = new UserService();
        CookService cookService = new CookService();
        NormalUserService normalUserService = new NormalUserService();
        HttpSession session = request.getSession();

        User user = new User(userName, userPassword);
        int result = userService.userLogin(user);
        if (result >= 0) {
            switch (result) {
                case 1:
                case 2: {
                    //设置登录信息
                    session.setAttribute("user", user);
                    NormalUser normalUser = normalUserService.getNormalUserByUserId(user.getId());
                    session.setAttribute("normalUser", normalUser);
                    session.setAttribute("shoppingCar", new ShoppingCar());
                    ProductMenu[] menus = new ProductMenuService().getAllMenus();
                    //设置菜单属性
                    session.setAttribute("menus", menus);
                    response.sendRedirect("NormalUserView/normalUserView.jsp");
                    break;
                }
                case 3: {
                    //设置操作对象
                    request.getSession().setAttribute("user", user);
                    Cook cook = cookService.getCookByUserId(user.getId());
                    request.getSession().setAttribute("cook", cook);
                    //设置厨师管理的菜单列表
                    ProductMenu[] cookMenus = new CookService().getCookMenuByCookId(cook.getId());
                    request.getSession().setAttribute("cookMenus", cookMenus);

                    response.sendRedirect("LoadOrdersServlet");
                    break;
                }
                case 4:
                case 5: {
                    break;
                }
            }

        } else {
            switch (result) {
                case UserService.LOGIN_ERROR: {
                    response.sendRedirect("UserView/loginFail.jsp?error=LoginError");
                    return;
                }
                case UserService.ACCOUNT_NOT_EXIST: {
                    response.sendRedirect("UserView/loginFail.jsp?error=AccountNotExist");
                    return;
                }
                case UserService.PASSWORD_ERROR: {
                    response.sendRedirect("UserView/loginFail.jsp?error=PasswordError");
                    return;
                }
            }

        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
