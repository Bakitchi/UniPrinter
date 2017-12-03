package Servlet;

import JavaBean.AdminBean;
import JavaBean.InfoBean;
import JavaBean.ItemBean;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: michael
 * @Date: 16-10-22 下午8:57
 * @Project: Uni-Pinter
 * @Package: Servlet
 */
@WebServlet(name = "HandleDetial")
public class HandleDetial extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        AdminBean adminBean = (AdminBean)request.getSession().getAttribute("adminBean");
        InfoBean infoBean = new InfoBean();
        request.getSession().setAttribute("infoBean", infoBean);
        String forward;
        if (adminBean == null || !adminBean.isStatus()) {
            forward = "/login.jsp";
            infoBean.setInfo("您还未登陆，请登录后重试。");
        } else {
            forward = "/userDetial.jsp";
        }
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(forward);
        requestDispatcher.forward(request, response);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
