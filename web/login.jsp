<%--
    Created by IntelliJ IDEA.
    User: michael
    Date: 16-10-22
    Time: 下午9:46
    To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="infoBean" scope="session" class="JavaBean.InfoBean"/>
<jsp:useBean id="adminBean" scope="session" class="JavaBean.AdminBean"/>
<% if (adminBean.isStatus()) {
    response.sendRedirect("showList?type=Brand");
 } %>
<!DOCTYPE HTML>
<html>
<head>
    <title>登陆</title>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no" />
    <!--[if lte IE 8]><script src="assets/js/ie/html5shiv.js"></script><![endif]-->
    <link rel="stylesheet" href="assets/css/main.css" />
    <link rel="stylesheet" href="assets/css/increase.css" />
    <!--[if lte IE 9]><link rel="stylesheet" href="assets/css/ie9.css" /><![endif]-->
    <!--[if lte IE 8]><link rel="stylesheet" href="assets/css/ie8.css" /><![endif]-->
</head>
<body>
    <div id="wrapper">
        <div class="container">
            <% if(infoBean == null || infoBean.getWarning() != null) {%>
            <div class="box">
                <h3><%=infoBean.getWarning()%></h3>
                <div class="closer"></div>
            </div>
            <% } %>
            <div class="12u inner row">
                <%--<div class="4u 12u$(small)">asd</div>--%>
                <div class="12u$ 12u$(small)">
                <section>
                    <h2>登陆</h2>
                        <section>
                            <form method="post" action="/login">
                                <div class="field first">
                                    <label>用户名</label>
                                    <input type="text" name="username" placeholder="用户名" />
                                </div>
                                <div class="field">
                                    <label>密码</label>
                                    <input type="password" name="password" placeholder="密码" />
                                </div>
                                <ul class="actions">
                                    <li><input type="submit" value="确定" class="special" /></li>
                                    <li><input type="reset" value="重置" /></li>
                                </ul>
                            </form>
                        </section>
                        <section class="split">
                    </section>
                </section>
                </div>
                <%--<div class="4u 12u$(small)"></div>--%>
            </div>
        </div>
    </div>
    <script src="assets/js/jquery.min.js"></script>
    <script src="assets/js/jquery.poptrox.min.js"></script>
    <script src="assets/js/skel.min.js"></script>
    <script src="assets/js/util.js"></script>
    <!--[if lte IE 8]><script src="assets/js/ie/respond.min.js"></script><![endif]-->
    <script src="assets/js/increase.js"></script>
    <script src="assets/js/main.js"></script>
</body>
</html>
