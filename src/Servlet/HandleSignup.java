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
 * @Date: 16-10-22 下午7:23
 * @Project: Uni-Pinter
 * @Package: Servlet
 */
@WebServlet(name = "HandleSignup")
public class HandleSignup extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String license = request.getParameter("license");
        String tel = request.getParameter("tel");
//        String email = request.getParameter("email");
        String forward;
        AdminBean adminBean = new AdminBean();
        InfoBean infoBean = new InfoBean();
        request.getSession().setAttribute("infoBean", infoBean);
        request.getSession().setAttribute("adminBean", adminBean);

        try {
            SQLConnector connector = new SQLConnector();
            String sql = "select * from admin where license='" + license + "'";
            ResultSet resultSet = connector.qurey(sql);
            if(resultSet.next() && resultSet.getString(AdminBean.USERNAME) == null) {
                adminBean.setUsername(username);
                adminBean.setId(resultSet.getInt(AdminBean.ID));
                adminBean.setExpiration(resultSet.getTimestamp(AdminBean.EXPIRATION));
                adminBean.setTel(tel);
                adminBean.setStatus(false);
                adminBean.setAuthority(false);
                sql = "UPDATE admin SET username='"+ username +"',password='"+ password +"',tel='"+ tel +"',authority='0' WHERE id='"+ adminBean.getId() +"'";
                connector.update(sql);
                forward = "/login.jsp";
            } else {
                infoBean.setWarning("此序列号不合法或已被使用。");
                forward = "/signup.jsp";
                adminBean.setStatus(false);
            }
        } catch (SQLException e) {
            infoBean.setError("数据库访问错误,请重试。");
            forward = "/signup.jsp";
            adminBean.setStatus(false);
            e.printStackTrace();
        }
        response.sendRedirect(forward);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(request.getRequestURI());
        requestDispatcher.forward(request, response);
    }
}
