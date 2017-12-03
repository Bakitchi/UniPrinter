<%--
    Created by IntelliJ IDEA.
    User: michael
    Date: 16-11-1
    Time: 下午9:37
    To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="infoBean" scope="session" class="JavaBean.InfoBean"/>
<!DOCTYPE HTML>
<html>
    <head>
        <title>Error</title>
        <meta charset="utf-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no" />
        <!--[if lte IE 8]><script src="../assets/js/ie/html5shiv.js"></script><![endif]-->
        <link rel="stylesheet" href="../assets/css/main.css" />
        <link rel="stylesheet" href="../assets/css/increase.css" />
        <!--[if lte IE 9]><link rel="stylesheet" href="../assets/css/ie9.css" /><![endif]-->
        <!--[if lte IE 8]><link rel="stylesheet" href="../assets/css/ie8.css" /><![endif]-->
    </head>
    <body>
        <div id="wrapper">
            <div class="container">
                <h1>好像发生了一些错误...</h1>
                <%if(infoBean.getWarning() != null && infoBean.getWarning().equals("1")) {%>
                    <h2>在试图访问<%=infoBean.getInfo()%>时发生如下错误</h2>
                <%} else {%>
                    <h2>在试图访问<%=request.getRequestURI()%>时发生如下错误</h2>
                <%}%>
                <p><%=infoBean.getError()%></p>
                <%if(request.getHeader("Referer") != null) {%>
                    <a href="<%=request.getHeader("Referer")%>" class="button special">返回之前页面</a>
                <%} else {%>
                    <a href="showList?type=Brand" class="button special">返回首页</a>
                <%}%>
            </div>
        </div>
    </body>
</html>