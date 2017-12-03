package Servlet;

import JavaBean.AdminBean;
import JavaBean.InfoBean;
import Util.SQLConnector;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @Author: michael
 * @Date: 16-10-25 下午1:55
 * @Project: Uni-Pinter
 * @Package: Servlet
 */
@WebServlet(name = "HandleEdit")
public class HandleEdit extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        String editType = request.getParameter("editType").trim();
        AdminBean adminBean = (AdminBean) request.getSession().getAttribute("adminBean");
        InfoBean infoBean = new InfoBean();
        request.getSession().setAttribute("infoBean", infoBean);
        String forward;
        if (adminBean == null || !adminBean.isStatus()) {
            forward = "/login.jsp";
            infoBean.setInfo("您还未登陆，请登录后重试。");
        }
        else {
            String sql;
            String query = null;
            String oldPass;
            if (editType.equals("changePassword")) {
                oldPass = request.getParameter("oldPasswd");
                query = "select * from admin where password='"+ oldPass +"' and '"+ adminBean.getId() +"'";
                sql = "UPDATE admin SET password='" + request.getParameter("password").trim() + "',expiration='"+ adminBean.getExpiration() +"' WHERE id='" + adminBean.getId() + "'";
            } else {
                sql = "UPDATE admin SET email='" + request.getParameter("email").trim() +
                        "',tel='" + request.getParameter("tel").trim()+
                        "',expiration='"+ adminBean.getExpiration() +
                        "',info='"+ request.getParameter("info").trim() +
                        "' WHERE id='" + adminBean.getId() + "'";
            }

            try {
                SQLConnector connector = new SQLConnector();
                if (editType.equals("changePassword")) {
                    ResultSet resultSet = connector.qurey(query);
                    if(resultSet.next()) {
                        connector.update(sql);
                    } else {
                        infoBean.setWarning("旧密码错误，请重新输入。");
                    }
                    SQLConnector.closeResultSet(resultSet);
                } else {
                    connector.update(sql);
                    adminBean.setEmail(request.getParameter("email").trim());
                    adminBean.setTel(request.getParameter("tel").trim());
                }
                forward = request.getHeader("Referer");
            } catch (SQLException e) {
                forward = "/WEB-INF/errorPage.jsp";
                infoBean.setError("数据库访问错误，请重试。");
                e.printStackTrace();
            }
        }
        response.sendRedirect(forward);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(request.getRequestURI());
        requestDispatcher.forward(request, response);
    }
}
