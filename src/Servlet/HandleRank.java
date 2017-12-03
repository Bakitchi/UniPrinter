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
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @Author: michael
 * @Date: 16-11-28 上午2:54
 * @Project: Uni-Pinter
 * @Package: Servlet
 */
@WebServlet(name = "HandleRank")
public class HandleRank extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        String Operation = request.getParameter("oper");
        String forward;
        InfoBean infoBean = new InfoBean();
        request.getSession().setAttribute("infoBean", infoBean);
        AdminBean adminBean = (AdminBean) request.getSession().getAttribute("adminBean");
        if (adminBean == null || !adminBean.isStatus()) {
            forward = "/login.jsp";
            infoBean.setWarning("您还未登陆，请登录后重试。");
        } else if(!adminBean.isAuthority()) {
            forward = "/showList?type=Brand";
            infoBean.setWarning("您的权限不足以进行此项操作。");
        } else {
            Item type;
            try {
                type = ParseEnum.parseEnum(Item.class, request.getParameter("type").trim());
            } catch (IllegalArgumentException e) {
                forward = "/WEB-INF/errorPage.jsp";
                e.printStackTrace();
                infoBean.setError("对不起，您修改的类型无法被系统识别， 请重试。");
                RequestDispatcher requestDispatcher = request.getRequestDispatcher(forward);
                requestDispatcher.forward(request, response);
                return;
            }
            int rank = Integer.parseInt(request.getParameter("rank"));
            int rank1;

            String sql;
            if (Operation == null || !Operation.equals("up") && !Operation.equals("down")) {
                forward = "/WEB-INF/errorPage";
                infoBean.setError("unknow operation.");
                RequestDispatcher requestDispatcher = request.getRequestDispatcher(forward);
                requestDispatcher.forward(request, response);
                return;
            } else {
                try {
                    forward = request.getHeader("Referer");
                    if(forward == null)
                        forward = "showList?type=Brand";
                    SQLConnector connector = new SQLConnector();
                    ResultSet resultSet;
                    if(type == Item.Model) {
                        resultSet = connector.qurey("select * from " + type + " where brand='"+ request.getParameter("brand") +"'");
                    } else if(type == Item.Image) {
                        resultSet = connector.qurey("select * from " + type + " where category='"+ request.getParameter("category") +"'");
                    } else {
                        resultSet = connector.qurey("select * from " + type);
                    }
                    resultSet.last();
                    int rows = resultSet.getRow();
                    if(rank == 1 && Operation.equals("up") || rank == rows && Operation.equals("down")) {
                        response.sendRedirect(forward);
                        return;
                    } else if(Operation.equals("up")){
                        rank1 = rank-1;
                    } else {
                        rank1 = rank+1;
                    }
                    sql = "UPDATE\n" +
                          "    "+type+" AS b1\n" +
                          "    JOIN "+type+" AS b2 ON\n" +
                          "           ( b1.rank="+rank+" and b2.rank="+rank1+")\n" +
                          "SET\n" +
                          "    b1.rank = b2.rank,\n" +
                          "    b2.rank = b1.rank;";
                    connector.update(sql);
                } catch (SQLException e) {
                    forward = "/WEB-INF/errorPage";
                    infoBean.setError("数据库访问错误,请重试。");
                    e.printStackTrace();
                    RequestDispatcher requestDispatcher = request.getRequestDispatcher(forward);
                    requestDispatcher.forward(request, response);
                    return;
                }
            }
        }
        response.sendRedirect(forward);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
