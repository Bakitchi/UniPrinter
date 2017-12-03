package Servlet;

import JavaBean.*;
import Util.ParseEnum;
import Util.SQLConnector;
import com.sun.org.apache.xpath.internal.operations.Mod;
import com.sun.rowset.CachedRowSetImpl;

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
 * @Date: 16-10-21 下午5:33
 * @Project: Uni-Pinter
 * @Package: Servlet
 */
@WebServlet(name = "HandleItemShow")
public class HandleItemShow extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        ShowListBean showListBean = new ShowListBean();
        String forward = null;
        String brand;
        String model;
        String category;
        String mode;
        Item type = null;
        AdminBean adminBean = (AdminBean)request.getSession().getAttribute("adminBean");
        InfoBean infoBean = new InfoBean();
        request.getSession().setAttribute("infoBean", infoBean);
        if(request.getParameter("type") == null) {
            type = Item.Brand;
        } else {
            try {
                type = ParseEnum.parseEnum(Item.class, request.getParameter("type").trim());
            } catch (IllegalArgumentException e) {
                forward = "/WEB-INF/errorPage.jsp";
                e.printStackTrace();
                infoBean.setError("对不起，您查询的类型无法被系统识别， 请重试。");
                RequestDispatcher requestDispatcher = request.getRequestDispatcher(forward);
                requestDispatcher.forward(request, response);
                return;
            }
        }
        int uid;
        if(request.getParameter("uid") == null) {
            uid = 0;
        } else {
            try {
                uid = Integer.parseInt(request.getParameter("uid").trim());
            } catch (NumberFormatException e) {
                uid = 0;
            }
        }
        int showPage;
        CachedRowSetImpl rowSet = null;
        request.getSession().setAttribute("showListBean", showListBean);
        showListBean.setInitialized(true);
        showListBean.setType(type);
        try {
            SQLConnector connector = new SQLConnector();
            String sql = null;
            if(adminBean == null || !adminBean.isStatus() || adminBean.isAuthority()) {
                if (adminBean == null || !adminBean.isStatus())
                    showListBean.setPageSize(11);
                else if(type == Item.Shell)
                    showListBean.setPageSize(11);
            } else {
                if(type == Item.Shell || type == Item.Model || type == Item.Brand)
                    showListBean.setPageSize(11);
            }
            forward = "/WEB-INF/index.jsp";
            switch (type) {
                case TShirt:
                    if(adminBean == null || !adminBean.isStatus()) {
                        RequestDispatcher requestDispatcher = request.getRequestDispatcher(forward);
                        requestDispatcher.forward(request, response);
                        return;
                    }
                    if(uid == 0)
                        sql = "SELECT * FROM tshirt";
                    else
                        sql = "SELECT * FROM tshirt WHERE (uid='0'||uid='" + uid + "')";
                    break;
                case Lighter:
                    if(adminBean == null || !adminBean.isStatus()) {
                        RequestDispatcher requestDispatcher = request.getRequestDispatcher(forward);
                        requestDispatcher.forward(request, response);
                        return;
                    }
                    if(uid == 0)
                        sql = "SELECT * FROM lighter";
                    else
                        sql = "SELECT * FROM lighter WHERE (uid='0'||uid='" + uid + "')";
                    break;
                case Shell:
                    brand = request.getParameter("brand");
                    model = request.getParameter("model");
                    if(adminBean == null || !adminBean.isStatus()) {
                        RequestDispatcher requestDispatcher = request.getRequestDispatcher(forward);
                        requestDispatcher.forward(request, response);
                        return;
                    }
                    if(uid == 0) {
                        if (brand == null && model == null)
                            sql = "SELECT * FROM shell";
                        else {
                            model = model.split(",")[0];
                            sql = "SELECT * FROM shell WHERE brand='" + brand + "' and model='" + model + "'";
                        }
                    }
                    else {
                        if (brand == null && model == null)
                            sql = "SELECT * FROM shell WHERE (uid='0'||uid='" + uid + "')";
                        else {
                            model = model.split(",")[0];
                            sql = "SELECT * FROM shell WHERE (uid='0'||uid='" + uid + "') and brand='" + brand + "' and model='" + model + "'";
                        }
                    }
                    break;
                case Brand:
                    if(uid == 0)
                        sql = "SELECT * FROM brand order by rank";
                    else
                        sql = "SELECT * FROM brand WHERE (uid='0'||uid='" + uid + "') order by rank";
                    break;
                case Model:
                    brand = request.getParameter("brand");
                    if(uid == 0)
                        sql = "SELECT * FROM model WHERE brand='" + brand + "' order by rank";
                    else
                        sql = "SELECT * FROM model WHERE  (uid='0'||uid='" + uid + "') and brand='" + brand + "' order by rank";
                    break;
                case Image:
                    category = request.getParameter("category");
                    mode = request.getParameter("mode");
                    if(uid == 0)
                        sql = "SELECT * FROM image WHERE category='" + category + "' order by rank";
                    else
                        sql = "SELECT * FROM image WHERE  (uid='0'||uid='" + uid + "') and category='" + category + "' order by rank";
                    if(mode != null && mode.equals("show"))
                        forward = "imageBank.jsp";
                    showListBean.setPageSize(10);
                    break;
                case Admin:
                    sql = "SELECT * FROM admin WHERE authority=0";
                    forward = "/WEB-INF/super-Admin.jsp";
                    break;
            }
            ResultSet resultSet = connector.qurey(sql);
            rowSet = new CachedRowSetImpl();
            rowSet.populate(resultSet);
            SQLConnector.closeResultSet(resultSet);
            showListBean.setRowSet(rowSet);
            rowSet.last();
            int row = rowSet.getRow();
            int pageAllCount = ((row % showListBean.getPageSize()) == 0) ? (row / showListBean.getPageSize()) : (row / showListBean.getPageSize() + 1);
            showListBean.setPageAllCount(pageAllCount);
            if(request.getParameter("page") == null) {
                showPage = 1;
                showListBean.setHavePrev(false);
                if(showListBean.getPageAllCount() > 1)
                    showListBean.setHaveNext(true);
            } else {
                showPage = Integer.parseInt(request.getParameter("page"));
                if(showPage >= showListBean.getPageAllCount()) {
                    showPage = showListBean.getPageAllCount();
                    showListBean.setHaveNext(false);
                    if(showListBean.getPageAllCount()>1)
                        showListBean.setHavePrev(true);
                    else showListBean.setHavePrev(false);
                } else {
                    showListBean.setHaveNext(true);
                }
                if(showPage <= 1) {
                    showPage = 1;
                    showListBean.setHavePrev(false);
                } else if(showPage <= showListBean.getPageAllCount()){
                    showListBean.setHavePrev(true);
                }
            }
            showListBean.setShowPage(showPage);
            showListBean.setBeanSet(this.genPageUnit(showPage, showListBean.getPageSize(), rowSet, type));

            infoBean.setError(request.getHeader("Referer")+"&"+request.getQueryString());
        } catch (SQLException e) {
            e.printStackTrace();
            forward = "/WEB-INF/errorPage.jsp";
            infoBean.setError("数据库访问错误，请重试。");
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        }
//        if(request.getParameter("forward") != null) {
//            forward = request.getParameter("forward");
//        }
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(forward);
        requestDispatcher.forward(request, response);
    }


    private ArrayList<Object> genPageUnit (int page, int pageSize, CachedRowSetImpl rowSet, Item type) throws SQLException, ArrayIndexOutOfBoundsException{
        ArrayList<Object> pageUnits = new ArrayList<Object>();
        try {
            rowSet.absolute((page - 1) * pageSize + 1);
            for (int i = 1; i <= pageSize; i++) {
                if(type == Item.Admin) {
                    AdminBean adminBean = new AdminBean();
                    adminBean.setId(rowSet.getInt(AdminBean.ID));
                    adminBean.setUsername(rowSet.getString(AdminBean.USERNAME));
                    adminBean.setTel(rowSet.getString(AdminBean.TEL));
                    adminBean.setEmail(rowSet.getString(AdminBean.EMAIL));
                    adminBean.setLicense(rowSet.getString(AdminBean.LICENSE));
                    adminBean.setExpiration(rowSet.getTimestamp(AdminBean.EXPIRATION));
                    adminBean.setInfo(rowSet.getString(AdminBean.INFO));
                    pageUnits.add(adminBean);
                } else {
                    ItemBean itemBean = new ItemBean();
                    itemBean.setId(rowSet.getInt(ItemBean.ID));
                    if(type == Item.Shell)
                        itemBean.setName(rowSet.getString("brand")+'-'+rowSet.getString("model")+','+rowSet.getString(ItemBean.NAME));
                    else
                        itemBean.setName(rowSet.getString(ItemBean.NAME));
                    itemBean.setInfo(rowSet.getString(ItemBean.INFO));
                    if(type == Item.Brand || type == Item.Model || type == Item.Image)
                        itemBean.setRank(rowSet.getInt(ItemBean.RANK));
                    itemBean.setType(type);
                    pageUnits.add(itemBean);
                }
                if (! rowSet.next())
                    break;
            }
            return pageUnits;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException();
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
            throw new ArrayIndexOutOfBoundsException();
        }
    }
}

