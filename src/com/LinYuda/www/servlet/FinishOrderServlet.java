package com.LinYuda.www.servlet;

import com.LinYuda.www.service.CookService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 厨师完成订单的Servlet
 */
@WebServlet(name = "FinishOrderServlet", urlPatterns = "/FinishOrderServlet")
public class FinishOrderServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long orderId = Long.parseLong(request.getParameter("orderId"));
        CookService cookService = new CookService();
        boolean result = cookService.finishOrderByOrderId(orderId);
        if (result) {
            //提交成功
            response.sendRedirect("CookView/finishOrderSuccess.jsp");
        } else {
            //提交失败
            response.sendRedirect("CookView/finishOrderFail.jsp");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
