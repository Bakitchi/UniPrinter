package Servlet;

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
 * @Date: 16-11-3 上午12:43
 * @Project: Uni-Pinter
 * @Package: Servlet
 */
@WebServlet(name = "HandleError")
public class HandleError extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Throwable throwable = (Throwable)request.getAttribute("javax.servlet.error.exception");
        Integer statusCode = (Integer)request.getAttribute("javax.servlet.error.status_code");
        String servletName = (String)request.getAttribute("javax.servlet.error.servlet_name");
        if (servletName == null){
            servletName = "Unknown";
        }
        String requestUri = (String)request.getAttribute("javax.servlet.error.request_uri");
        if (requestUri == null){
            requestUri = "Unknown";
        }
        InfoBean infoBean = new InfoBean();
        request.getSession().setAttribute("infoBean", infoBean);
        String forward = "/WEB-INF/errorPage.jsp";
        String errorInfo;
        if (throwable == null && statusCode == null){
            errorInfo = "错误信息丢失。";
        } else if(statusCode != null) {
            errorInfo = "错误代码："+ statusCode;
        } else {
            errorInfo = throwable.getMessage();
        }
        infoBean.setWarning("1");
        infoBean.setInfo(requestUri);
        infoBean.setError(errorInfo);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(forward);
        requestDispatcher.forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
