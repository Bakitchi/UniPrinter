<%--
    Created by IntelliJ IDEA.
    User: michael
    Date: 16-10-29
    Time: 下午5:39
    To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="infoBean" scope="session" class="JavaBean.InfoBean"/>
<html>
<head>
    <title>注册</title>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no" />
    <!--[if lte IE 8]><script src="assets/js/ie/html5shiv.js"></script><![endif]-->
    <link rel="stylesheet" href="assets/css/main.css" />
    <link rel="stylesheet" href="assets/css/increase.css" />
    <!--[if lte IE 9]><link rel="stylesheet" href="assets/css/ie9.css" /><![endif]-->
    <!--[if lte IE 8]><link rel="stylesheet" href="assets/css/ie8.css" /><![endif]-->
</head>
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
                        <h2>注册</h2>
                        <section>
                            <form id="signup" method="post" action="signup">
                                <div class="field first">
                                    <label>用户名</label>
                                    <input type="text" name="username" placeholder="用户名" />
                                </div>
                                <div class="field">
                                    <label>密码</label>
                                    <input type="password" id="password" name="password" placeholder="密码" />
                                </div>
                                <div class="field">
                                    <label>确认密码</label>
                                    <input type="password" id="confirm" name="confirm" placeholder="确认密码" />
                                </div>
                                <div class="field">
                                    <label>序列号</label>
                                    <input type="text" name="license" placeholder="序列号">
                                </div>
                                <div class="field">
                                    <label>联系电话</label>
                                    <input type="text" name="tel" placeholder="电话">
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
<script src="assets/js/util.js"></script>
<script src="assets/js/jquery.validate.min.js"></script>
</body>
</html>
