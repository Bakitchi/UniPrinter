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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

/**
 * @Author: michael
 * @Date: 16-10-22 下午7:07
 * @Project: Uni-Pinter
 * @Package: Servlet
 */
@WebServlet(name = "HandleLogin")
public class HandleLogin extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String forward;
        AdminBean adminBean = new AdminBean();
        InfoBean infoBean = new InfoBean();
        request.getSession().setAttribute("infoBean", infoBean);
        request.getSession().setAttribute("adminBean", adminBean);

        try {
            SQLConnector connector = new SQLConnector();
            String sql = "select * from admin where username='"+ username+"' and password='"+password+"'";
            ResultSet resultSet = connector.qurey(sql);
            if(resultSet.next()) {
                adminBean.setId(resultSet.getInt(AdminBean.ID));
                adminBean.setUsername(resultSet.getString(AdminBean.USERNAME));
                adminBean.setAuthority(resultSet.getBoolean(AdminBean.AUTHORITY));
                adminBean.setTel(resultSet.getString(AdminBean.TEL));
                adminBean.setEmail(resultSet.getString(AdminBean.EMAIL));
                adminBean.setExpiration(resultSet.getTimestamp(AdminBean.EXPIRATION));
//                if(adminBean.getExpiration().after(new Timestamp()))
                adminBean.setStatus(true);
                SQLConnector.closeResultSet(resultSet);
                if (adminBean.isAuthority()) {
                    forward = "/showList?type=Admin";
                } else {
                    Date date = new Date();
                    boolean isOutdated = date.after(adminBean.getExpiration());
                    if(isOutdated) {
                        forward = "/login.jsp";
                        adminBean.setStatus(false);
                        infoBean.setWarning("您的会员已过期，请联系系统管理员续费");
                    } else {
                        forward = "/showList?type=Shell";
                    }
                }
                SQLConnector.closeResultSet(resultSet);
            } else {
                infoBean.setWarning("用户名或密码错误，请确认后重新登录。");
                forward = "/login.jsp";
                adminBean.setStatus(false);
            }
        } catch (SQLException e) {
            infoBean.setError("数据库访问错误,请重试。");
            forward = "/login.jsp";
            adminBean.setStatus(false);
            e.printStackTrace();
        }
        response.sendRedirect(forward);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
