<%@ page import="JavaBean.AdminBean" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="java.time.LocalDateTime" %><%--
    Created by IntelliJ IDEA.
    User: michael
    Date: 16-10-29
    Time: 下午9:44
    To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="showListBean" scope="session" class="JavaBean.ShowListBean"/>
<jsp:useBean id="infoBean" scope="session" class="JavaBean.InfoBean"/>
<jsp:useBean id="adminBean" scope="session" class="JavaBean.AdminBean"/>
<% if (adminBean == null || !adminBean.isStatus() || !adminBean.isAuthority()) {
    infoBean.setWarning("您还未登陆或权限不足以查看此页面，请登录后重试。"); %>
    <jsp:forward page="/login.jsp"/>
<% } %>
<!DOCTYPE HTML>
<html>
<head>
    <title>Super-Admin</title>
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
        <header id="header">
            <h1><a href="showList?type=Admin"><strong><%=adminBean.getUsername()%></strong></a></h1>
            <nav>
                <ul>
                    <li><a href="#datalist" class="icon fa-list-ul">功能</a></li>
                </ul>
            </nav>
        </header>
        <div class="container">
            <% if(infoBean == null || infoBean.getInfo() != null) {%>
            <div class="box">
                <h3 class="special"><%=infoBean.getInfo()%></h3>
                <div class="closer"></div>
            </div>
            <% } %>
            <% if(showListBean.getBeanSet() != null && showListBean.getBeanSet().size() != 0) {
                int index = 0; %>
            <h1>尊敬的<%=adminBean.getUsername()%></h1>
            <h2>下面列出所有自管理员信息</h2>
            <div class="table-wrapper">
                <table>
                    <thead>
                        <tr>
                            <%--<th>序号</th>--%>
                            <th>用户名</th>
                            <th>电话</th>
                            <th>email</th>
                            <th>状态</th>
                            <th>信息</th>
                            <th>序列号</th>
                            <th>会员到期时间</th>
                            <th>操作</th>
                        </tr>
                    </thead>
                    <tbody>
                    <%  for (Object subAdminBean: showListBean.getBeanSet()) {
                        Date date = new Date();
                        index++;
                        boolean isOutdated = date.after(((AdminBean)subAdminBean).getExpiration()); %>
                        <tr>
                            <%--<td><%=((AdminBean)subAdminBean).getId()%></td>--%>
                            <td><%=((AdminBean)subAdminBean).getUsername()%></td>
                            <td><%=((AdminBean)subAdminBean).getTel()%></td>
                            <td><%=((AdminBean)subAdminBean).getEmail()%></td>
                            <td><%if(((AdminBean)subAdminBean).getUsername() == null){%>未激活<%} else if(isOutdated) {%>已过期<%} else {%>正常使用<%}%></td>
                            <td><%=((AdminBean)subAdminBean).getInfo()%></td>
                            <td><%=((AdminBean)subAdminBean).getLicense()%></td>
                            <td><%=((AdminBean)subAdminBean).getExpiration()%></td>
                            <td>
                                <a class="dangerousZone" href="delete?type=<%=showListBean.getType()%>&id=<%=((AdminBean)subAdminBean).getId()%>">删除</a>
                                <a class="dangerousZone" href="setExpiration?newExp=1&index=<%=(showListBean.getShowPage()-1)*showListBean.getPageSize()+index-1%>">增加一个月</a>
                                <a class="dangerousZone" href="setExpiration?newExp=3&index=<%=(showListBean.getShowPage()-1)*showListBean.getPageSize()+index-1%>">增加三个月</a>
                            </td>
                        </tr>
                        <% } %>
                        <%--<article class='thumb'>--%>
                        <%--<button id="prevpage" <% if(!showListBean.isHavePrev()) {%>class="disabled"<% } %>>--%>
                        <%--<div>--%>
                        <%--<h1>Prev</h1>--%>
                        <%--<i class="fa fa-chevron-left fa-5x"></i>--%>
                        <%--<input type="hidden" value="<%=showListBean.getShowPage() %>"/>--%>
                        <%--</div>--%>
                        <%--</button>--%>
                        <%--<button id="nextpage" <% if(!showListBean.isHaveNext()) {%>class="disabled"<% } %>>--%>
                        <%--<div>--%>
                        <%--<h1>Next</h1>--%>
                        <%--<i class="fa fa-chevron-right fa-5x"></i>--%>
                        <%--<input type="hidden" value="<%=showListBean.getShowPage() %>"/>--%>
                        <%--</div>--%>
                        <%--</button>--%>
                        <%--</article>--%>
                    </tbody>
                </table>
            </div>
            <%if(showListBean.isHavePrev() || showListBean.isHaveNext()) {%>
                <ul class="pagination">
                    <li <% if(!showListBean.isHavePrev()) {%>class="disabled"<% } %>><a href="showList?type=Admin&page=<%=showListBean.getShowPage()-1%>">上一页</a></li>
                    <li class="active"><span><%=showListBean.getShowPage()%></span></li>
                    <li <% if(!showListBean.isHaveNext()) {%>class="disabled"<% } %>><a href="showList?type=Admin&page=<%=showListBean.getShowPage()+1%>">下一页</a></li>
                </ul>
            <%}%>
            <% } else {%>
            <h1>尊敬的<%=adminBean.getUsername()%></h1>
            <h2>还未创建任何管理员账号，点击按钮创建</h2>
            <form method="post" action="createAccount">
                <ul class="actions">
                    <li>
                        <input type="submit" class="fit" value="创建新的管理员帐号">
                    </li>
                </ul>
            </form>
            <% } %>
        </div>
        <%--<div id="datalist" class="panel">--%>

        <%--</div>--%>
        <footer id="datalist" class="panel">
            <form method="post" class="row" action="search">
                <input type="hidden" name="type" value="Admin">
                <div class="field first 10u 12u$(small)">
                    <input type="text" placeholder="Search" name="query">
                </div>
                <div class="field 2u$ 12u$(small)">
                    <input type="submit" value="确定" class="special" />
                </div>
            </form>
            <hr>
            <div class="inner row">
                <div class="6u 12u$(small)">
                    <section>
                        <h2 class="greetings"></h2>
                        <p>欢迎使用超级管理员系统<br>
                        请选择所要进行的操作</p>
                    </section>
                    <h2>点击按钮创建新管理员帐号</h2>
                    <form method="post" action="createAccount">
                        <ul class="actions">
                            <li>
                                <input type="submit" class="fit special" value="创建新的管理员帐号">
                            </li>
                        </ul>
                    </form>
                    <hr>
                    <ul class="actions">
                        <li><a href="showList?type=Shell" class="button">作品页面</a> <a href="logout" class="button">退出登录</a></li>
                    </ul>
                </div>
                <div class="6u$ 12u$(small)">
                    <h2>更改密码</h2>
                    <form method="post" id="changePasswd" action="edit?editType=changePassword">
                        <div class="field first">
                            <input name="oldPasswd" type="password" placeholder="请输入旧密码">
                        </div>
                        <div class="field">
                            <input name="password"  type="password" placeholder="请输入新密码">
                        </div>
                        <div class="field">
                            <input name="confirm" type="password" placeholder="请确认新密码">
                        </div>
                        <ul class="actions">
                            <li>
                                <input type="submit" class="special" value="确定">
                                <input type="reset" value="重置">
                            </li>
                        </ul>
                    </form>
                    <p class="copyright">
                        &copy; <strong>Uni-Printer</strong> 2016. All rights reserved.
                    </p>
                </div>
            </div>
        </footer>
    </div>
    <!-- Scripts -->
    <script src="../assets/js/jquery.min.js"></script>
    <script src="../assets/js/jquery.poptrox.min.js"></script>
    <script src="../assets/js/skel.min.js"></script>
    <script src="../assets/js/util.js"></script>
    <!--[if lte IE 8]><script src="../assets/js/ie/respond.min.js"></script><![endif]-->
    <script src="../assets/js/jquery.validate.min.js"></script>
    <script src="../assets/js/increase.js"></script>
    <script src="../assets/js/main.js"></script>
</body>
</html>
