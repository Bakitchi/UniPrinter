package Servlet;

import JavaBean.AdminBean;
import JavaBean.InfoBean;
import Util.RandomString;
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
import java.util.Random;

/**
 * @Author: michael
 * @Date: 16-10-25 上午9:10
 * @Project: Uni-Pinter
 * @Package: Servlet
 */
@WebServlet(name = "CreateAccount")
public class CreateAccount extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        AdminBean adminBean = (AdminBean) request.getSession().getAttribute("adminBean");
        InfoBean infoBean = new InfoBean();
        request.getSession().setAttribute("infoBean", infoBean);
        String forward;
        if (adminBean == null || !adminBean.isStatus()) {
            forward = "/login.jsp";
            infoBean.setWarning("您还未登陆，请登录后重试。");
        } else if(!adminBean.isAuthority()) {
            forward = "/showList?type=Brand";
            infoBean.setWarning("您的权限不足以进行此项操作。");
        } else {
            String license = RandomString.RandomString(10);
            try {
                SQLConnector connector = new SQLConnector();
                String sql = "INSERT INTO admin(license) VALUES('"+ license +"')";
                connector.update(sql);
                infoBean.setInfo("新创建的序列号为： " + license);
                forward = "/showList?type=Admin";
            } catch (SQLException e) {
                infoBean.setError("数据库访问错误,请重试。");
                forward = "/showList?type=Admin";
                e.printStackTrace();
            }
        }
        response.sendRedirect(forward);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
