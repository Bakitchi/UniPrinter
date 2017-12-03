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
import java.util.ArrayList;

/**
 * @Author: michael
 * @Date: 16-10-22 下午9:03
 * @Project: Uni-Pinter
 * @Package: Servlet
 */
@WebServlet(name = "HandleSearch")
public class HandleSearch extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        String forward;
        String query = request.getParameter("query");
        Item type;
        String sql;
        AdminBean adminBean = (AdminBean)request.getSession().getAttribute("adminBean");
        InfoBean infoBean = new InfoBean();
        request.getSession().setAttribute("infoBean", infoBean);
        try {
            type = ParseEnum.parseEnum(Item.class, request.getParameter("type").trim());
            if(type == Item.Admin) {
                sql = "select * from " + type + " where tel='" + query + "'";
            } else if(type != Item.TShirt && type != Item.Lighter && type != Item.Shell) {
                sql = "select * from " + type + " where name='" + query + "'";
            } else {
                int uid = adminBean.getId();
                sql = "select * from " + type + " where id='" + query + "' and (uid='0'||uid='" + uid + "')";
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            forward = "/WEB-INF/errorPage.jsp";
            infoBean.setError("对不起，您查询的类型无法被系统识别， 请重试。");
            RequestDispatcher requestDispatcher = request.getRequestDispatcher(forward);
            requestDispatcher.forward(request, response);
            return;
        }
        if(type == Item.Admin || type == Item.Shell || type == Item.Lighter || type == Item.TShirt) {
            if (adminBean == null || !adminBean.isStatus()) {
                forward = "/login.jsp";
                infoBean.setWarning("您还未登陆，请登录后重试。");
                RequestDispatcher requestDispatcher = request.getRequestDispatcher(forward);
                requestDispatcher.forward(request, response);
                return;
            } else if (type == Item.Admin && !adminBean.isAuthority()) {
                forward = "/showList?type=Brand";
                infoBean.setWarning("您的权限不足以进行此项操作。");
                RequestDispatcher requestDispatcher = request.getRequestDispatcher(forward);
                requestDispatcher.forward(request, response);
                return;
            }
        }
        try {
            SQLConnector connector = new SQLConnector();
            ResultSet resultSet = connector.qurey(sql);
            if (resultSet.next()) {
                ShowListBean showListBean = new ShowListBean();
                request.getSession().setAttribute("showListBean", showListBean);

                ArrayList<Object> pageUnits = new ArrayList<Object>();
                if(type == Item.Admin){
                    AdminBean subAdmin = new AdminBean();
                    subAdmin.setId(resultSet.getInt(AdminBean.ID));
                    subAdmin.setUsername(resultSet.getString(AdminBean.USERNAME));
                    subAdmin.setTel(resultSet.getString(AdminBean.TEL));
                    subAdmin.setEmail(resultSet.getString(AdminBean.EMAIL));
                    subAdmin.setLicense(resultSet.getString(AdminBean.LICENSE));
                    subAdmin.setExpiration(resultSet.getTimestamp(AdminBean.EXPIRATION));
                    adminBean.setInfo(resultSet.getString(AdminBean.INFO));
                    pageUnits.add(subAdmin);
                    forward = "/WEB-INF/super-Admin.jsp";
                } else {
                    ItemBean itemBean = new ItemBean();
                    itemBean.setId(resultSet.getInt(ItemBean.ID));
                    itemBean.setName(resultSet.getString(ItemBean.NAME));
                    itemBean.setInfo(resultSet.getString(ItemBean.INFO));
                    if(type == Item.Brand || type == Item.Image || type == Item.Model)
                        itemBean.setRank(resultSet.getInt(ItemBean.RANK));
                    itemBean.setType(type);
                    pageUnits.add(itemBean);
                    forward = "/WEB-INF/index.jsp";
                }
                showListBean.setBeanSet(pageUnits);
                showListBean.setInitialized(true);
                showListBean.setPageAllCount(1);
                showListBean.setShowPage(1);
                showListBean.setType(type);
                SQLConnector.closeResultSet(resultSet);
            } else {
                infoBean.setError("未找到所查找的项目，请重试。");
                forward = "/WEB-INF/errorPage.jsp";
            }
        } catch (SQLException e) {
            infoBean.setError("数据库访问错误,请重试。");
            forward = "/WEB-INF/errorPage.jsp";
            e.printStackTrace();
        }
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(forward);
        requestDispatcher.forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
