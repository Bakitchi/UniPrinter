package Servlet;

import JavaBean.AdminBean;
import JavaBean.InfoBean;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: michael
 * @Date: 16-11-1 上午9:46
 * @Project: Uni-Pinter
 * @Package: Servlet
 */
@WebServlet(name = "HandleLogout")
public class HandleLogout extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        String forward;
        AdminBean adminBean = (AdminBean) request.getSession().getAttribute("adminBean");
        InfoBean infoBean = new InfoBean();
        request.getSession().setAttribute("infoBean", infoBean);
        if(adminBean == null || adminBean.getUsername() == null) {
            infoBean.setInfo("您还未登录");
            forward = "login.jsp";
            adminBean.setStatus(false);
        }
        else {
            request.getSession().invalidate();
            forward = "/";
        }
        response.sendRedirect(forward);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
