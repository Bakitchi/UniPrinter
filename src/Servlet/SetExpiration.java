package Servlet;

import JavaBean.AdminBean;
import JavaBean.InfoBean;
import JavaBean.ShowListBean;
import Util.SQLConnector;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;

/**
 * @Author: michael
 * @Date: 16-10-25 上午10:59
 * @Project: Uni-Pinter
 * @Package: ${PACKAGE_NAME}
 */
@WebServlet(name = "SetExpiration")
public class SetExpiration extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        AdminBean adminBean = (AdminBean)request.getSession().getAttribute("adminBean");
        InfoBean infoBean = new InfoBean();
        request.getSession().setAttribute("infoBean", infoBean);
        String forward = null;
        if (adminBean == null || !adminBean.isStatus()) {
            forward = "/login.jsp";
            infoBean.setWarning("您还未登陆，请登录后重试。");
        } else if(!adminBean.isAuthority()) {
            forward = "/showList?type=Brand";
            infoBean.setWarning("您的权限不足以进行此项操作。");
        } else {
            ShowListBean showListBean = (ShowListBean)request.getSession().getAttribute("showListBean");
            try {
                int index = Integer.parseInt(request.getParameter("index"));
                int id = ((AdminBean)showListBean.getBeanSet().get(index)).getId();
                int newExp = Integer.parseInt(request.getParameter("newExp"));
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(((AdminBean)showListBean.getBeanSet().get(index)).getExpiration());
                calendar.add(Calendar.MONTH, newExp);
                Timestamp newTime = new Timestamp(calendar.getTimeInMillis());
                SQLConnector connector = new SQLConnector();
                String sql = "UPDATE admin SET expiration='"+ newTime +"' where id='"+ id +"'";
                connector.update(sql);
                forward = "/showList?type=Admin";
            } catch (SQLException e) {
                infoBean.setError("数据库访问错误,请重试。");
                e.printStackTrace();
            }
        }
        response.sendRedirect(forward);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
