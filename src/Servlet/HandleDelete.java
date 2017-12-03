package Servlet;

import JavaBean.*;
import Util.ParseEnum;
import Util.SQLConnector;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @Author: michael
 * @Date: 16-10-25 下午2:22
 * @Project: Uni-Pinter
 * @Package: ${PACKAGE_NAME}
 */
@WebServlet(name = "HandleDelete")
public class HandleDelete extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        int id = Integer.parseInt(request.getParameter("id"));
        Item type;
        String forward = request.getHeader("Referer");
        InfoBean infoBean = new InfoBean();
        request.getSession().setAttribute("infoBean", infoBean);
        if(forward == null)
            forward = "/showList?type=Brand";
        try {
            type = ParseEnum.parseEnum(Item.class, request.getParameter("type").trim());
        } catch (IllegalArgumentException e) {
            forward = "/WEB-INF/errorPage.jsp";
            e.printStackTrace();
            infoBean.setError("对不起，您删除的类型无法被系统识别， 请重试。");
            RequestDispatcher requestDispatcher = request.getRequestDispatcher(forward);
            requestDispatcher.forward(request, response);
            return;
        }
        AdminBean adminBean = (AdminBean) request.getSession().getAttribute("adminBean");
        if(adminBean == null || !adminBean.isStatus()) {
            forward = "/login.jsp";
            infoBean.setWarning("您还未登陆，请登录后重试。");
        } else if(!adminBean.isAuthority() && type == Item.Admin) {
            forward = request.getHeader("Referer");
            infoBean.setWarning("您的权限不足以进行此项操作。");
        } else {
            String sql;
            if(type == Item.Admin) {
                sql = "DELETE FROM admin WHERE id='"+ id +"'";
            } else {
                sql = "DELETE FROM "+ type +" WHERE id='"+ id +"'";
            }
            try {
                SQLConnector connector = new SQLConnector();
                String path;
                String name = null;
                if(type != Item.Admin) {
                    ResultSet resultSet = connector.qurey("select * from "+ type +" where id='"+id+"'");
                    if(resultSet.next()) {
                        path = resultSet.getString(ItemBean.INFO);
                        File image = new File(getServletContext().getRealPath(path));
                        if(image.exists())
                            System.out.println(image.delete());
                        name = resultSet.getString(ItemBean.NAME);
                    }
                }
                if(type == Item.Model || type == Item.Image|| type == Item.Brand) {
                    ResultSet rankSet = connector.qurey("select * from "+ type +" where id='"+id+"'");
                    rankSet.next();
                    int rank = rankSet.getInt("rank");
                    connector.update(sql);
                    String key;
                    int rows;
                    ResultSet resultSet;

                    if (type == Item.Model) {
                        key = "and brand='" + request.getParameter("brand") + "'";
                        resultSet = connector.qurey("select * from " + type + " where brand='" + request.getParameter("brand") + "'");
                        sql = "DELETE FROM shell WHERE model='"+ name +"'";
                        connector.update(sql, false);
                    } else if (type == Item.Image) {
                        key = "and category='" + request.getParameter("category") + "'";
                        resultSet = connector.qurey("select * from " + type + " where category='" + request.getParameter("category") + "'");
                    } else {
                        key = "";
                        resultSet = connector.qurey("select * from " + type);
                        sql = "DELETE FROM model WHERE brand='"+ name +"'";
                        connector.update(sql, false);
                        sql = "DELETE FROM shell WHERE brand='"+ name +"'";
                        connector.update(sql, false);
                    }

                    if (resultSet != null) {
                        resultSet.last();
                        rows = resultSet.getRow();
                    } else {
                        rows = 0;
                    }
                    if (rank <= rows) {
                        connector.update("update " + type + " set rank='" + rank + "' where rank='" + (rows + 1) + "' "+ key);
                    }
                } else {
                    connector.update(sql);
                }
                forward = request.getHeader("Referer");
                infoBean.setInfo("已删除");
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
