<%@ page import="JavaBean.Item" %>
<%--
    Created by IntelliJ IDEA.
    User: michael
    Date: 16-10-22
    Time: 下午9:44
    To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="infoBean" scope="session" class="JavaBean.InfoBean"/>
<jsp:useBean id="showListBean" scope="session" class="JavaBean.ShowListBean"/>
<jsp:useBean id="adminBean" scope="session" class="JavaBean.AdminBean"/>
<!DOCTYPE HTML>
<html>
<head>
    <title>uv打印机个性化定制</title>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no" />
    <!--[if lte IE 8]><script src="../assets/js/ie/html5shiv.js"></script><![endif]-->
    <link rel="stylesheet" href="../assets/css/main.css" />
    <link rel="stylesheet" href="../assets/css/increase.css" />
    <!--[if lte IE 9]><link rel="stylesheet" href="../assets/css/ie9.css" /><![endif]-->
    <!--[if lte IE 8]><link rel="stylesheet" href="../assets/css/ie8.css" /><![endif]-->
    <script src="../assets/js/jquery.min.js"></script>
</head>

<body>
    <!-- Wrapper -->
    <div id="wrapper">
        <!-- Header -->
        <header id="header">
            <h1><a href="showList?type=<%if(adminBean.isStatus()){%>Shell<%} else {%>Brand<%}%>"><strong><% if(adminBean != null && adminBean.isStatus()) {%><%=adminBean.getUsername()%><%} else {%>个性DIY<%}%></strong></a></h1>
            <nav>
                <ul>
                    <%--<li><a href="#" class="active">Shell</a></li>--%>
                    <%--<li><a href="#">T-Shirt</a></li>--%>
                    <li><a href="#search">搜索</a></li>
                    <li><a href="#datalist"><%switch (showListBean.getType()) {
                                                case TShirt:
                                                    out.print("T恤");
                                                    break;
                                                case Lighter:
                                                    out.print("打火机");
                                                    break;
                                                default:
                                                    out.print("手机壳");
                                                    break;
                                            }%></a></li>
                    <li><a href="#footer" class="icon fa-list-ul" id="admin">管理员入口</a></li>
                </ul>
            </nav>
        </header>
        <!-- Main -->
        <div id="main">
            <%-- Construct query string --%>
            <% String queryString;
               String accessStr = "#";
               String addStr = null;
               String uid;
                if(adminBean.isStatus())
                    uid = adminBean.getId()+"";
                else if(request.getParameter("uid") != null)
                    uid = request.getParameter("uid");
                else uid = "0";
            %>
            <%if(showListBean.getType() == Item.Shell){
                if(request.getParameter("brand") == null || request.getParameter("model") == null)
                    queryString = "type=Shell&uid=" +uid;
                else
                    queryString = "type=Shell&brand=" + request.getParameter("brand") + "&model="+ request.getParameter("model") + "&uid=" +uid;
            } else if(showListBean.getType() == Item.Model) {
                queryString = "type=Model&brand=" + request.getParameter("brand") + "&uid=" +uid;
            } else if(showListBean.getType() == Item.Image) {
                queryString = "type=Image&category=" + request.getParameter("category") + "&mode=edit" + "&uid=" +uid;
            } else {
                queryString = "type=" + showListBean.getType() + "&uid=" +uid;
            }%>

            <% if(showListBean.getBeanSet() != null && showListBean.getBeanSet().size() != 0) {
                for (Object itemBean: showListBean.getBeanSet()) { %>
            <article class="thumb">
                <%if(showListBean.getType() == Item.Brand){
                    accessStr = "showList?type=Model&brand="+((JavaBean.ItemBean)itemBean).getName() + "&uid=" +uid;
                } else if(showListBean.getType() == Item.Model) {
                    if(adminBean.isStatus() && adminBean.isAuthority()) {
                        accessStr = "showList?type=Shell&brand=" + request.getParameter("brand") + "&model=" + ((JavaBean.ItemBean) itemBean).getName() + "&uid=" +uid;
                        addStr = "../editor.jsp?type=Shell&brand="+request.getParameter("brand")+"&model="+((JavaBean.ItemBean)itemBean).getName() + "&uid=" +uid;
                    }
                    else accessStr = "../editor.jsp?type=Shell&brand="+request.getParameter("brand")+"&model="+((JavaBean.ItemBean)itemBean).getName() + "&uid=" +uid;
//                    accessStr = "../editor.jsp?" + queryString;
                } else {
                    accessStr = request.getContextPath()+((JavaBean.ItemBean)itemBean).getInfo();
                }%>
                <%if(adminBean != null && adminBean.isStatus() && (adminBean.isAuthority() || (!adminBean.isAuthority() && showListBean.getType() == Item.Shell))) {%>
                    <a href="#actions" class="image">
                <%} else {%>
                    <a href="<%=accessStr%>" class="image">
                <%}%>
                    <img src="<%=request.getContextPath() %><% out.print(((JavaBean.ItemBean)itemBean).getInfo().trim()); %>">
                </a>
                <h2><%if(showListBean.getType() == Item.Model) {%><%=((JavaBean.ItemBean)itemBean).getName().split(",")[0]%><%} else if(showListBean.getType() != Item.Shell) {%><%=((JavaBean.ItemBean)itemBean).getName()%><%} else {%>id: <%=((JavaBean.ItemBean)itemBean).getId()%> <%=((JavaBean.ItemBean)itemBean).getName().split(",")[0]%><%}%></h2>
                <input type="hidden" name="accessStr" value="<%=accessStr%>">
                <input type="hidden" name="addStr" value="<%=addStr%>">
                <input type="hidden" name="rank" value="<%=((JavaBean.ItemBean)itemBean).getRank()%>">
                <input type="hidden" name="id" value="<%=((JavaBean.ItemBean)itemBean).getId()%>">
            </article>
                <% } %>
                <% if(showListBean.getType() != Item.Shell && adminBean.isStatus() && adminBean.isAuthority()) {%>
                <article class="thumb">
                    <%--<a href="<%if(showListBean.getType() == Item.Shell) {%>../editor.jsp?<%=queryString%><%} else {%>../upload.jsp?<%=queryString%><%}%>" data-poptrox="iframe,800x500" class="add button">--%>
                    <%--<a href="../editor.jsp?<%=queryString%>" data-poptrox="iframe,800x500" class="add button">--%>
                    <a href="../upload.jsp?<%=queryString%>" class="add button">
                        <div>
                            <h1>Add</h1>
                            <i class="fa fa-plus fa-5x"></i>
                        </div>
                    </a>
                    <br>
                </article>
                <%} else if(showListBean.getType() == Item.TShirt || showListBean.getType() == Item.Lighter) {%>
                <article class="thumb">
                    <a href="../editor.jsp?<%=queryString%>" class="add button">
                        <div>
                            <h1>Add</h1>
                            <i class="fa fa-plus fa-5x"></i>
                        </div>
                    </a>
                    <br>
                </article>
                <%}%>
                <%if(showListBean.isHavePrev() || showListBean.isHaveNext()) {%>
                <article class='thumb'>
                    <a href="showList?<%=queryString%>&page=<%=showListBean.getShowPage() - 1%>" id="prevpage" class="button <% if(!showListBean.isHavePrev()) {%>disabled<% } %>">
                        <div>
                            <h1>上页</h1>
                            <i class="fa fa-chevron-left fa-5x"></i>
                        </div>
                    </a>
                    <a href="showList?<%=queryString%>&page=<%=showListBean.getShowPage() + 1%>" id="nextpage"  class="button <% if(!showListBean.isHaveNext()) {%>disabled<% } %>">
                        <div>
                            <h1>下页</h1>
                            <i class="fa fa-chevron-right fa-5x"></i>
                        </div>
                    </a>
                </article>
                <%}%>
                <%--if there is no data in the bean.--%>
            <% } else if(showListBean.getType() != Item.Shell && adminBean.isStatus() && adminBean.isAuthority()){%><%--showListBean.getType() == Item.TShirt || showListBean.getType() == Item.Lighter || --%>
            <article class="thumb">
                <a href="../upload.jsp?<%=queryString%>" class="add button">
                <%--<a href="../editor.jsp?<%=queryString%>" data-poptrox="iframe,800x500" class="add button">--%>
                    <div>
                        <h1>Add</h1>
                        <i class="fa fa-plus fa-5x"></i>
                    </div>
                </a>
                <br>
            </article>
            <%} else if(showListBean.getType() == Item.TShirt || showListBean.getType() == Item.Lighter) {%>
            <article class="thumb">
                <a href="../editor.jsp?<%=queryString%>" class="add button">
                    <div>
                        <h1>Add</h1>
                        <i class="fa fa-plus fa-5x"></i>
                    </div>
                </a>
                <br>
            </article>
            <%} else {%>
                <article class="thumb">
                    <a class="add">
                        <div>
                            <h1>敬请期待...</h1>
                            <i class="fa fa-spinner fa-spin fa-5x"></i>
                        </div>
                    </a>
                    <br>
                </article>
            <%}%>
        </div>
        <div id="datalist" class="panel">
            <div class="inner row">
                <div class="12u 12u$(small)">
                    <%if((adminBean != null && adminBean.isStatus()) && showListBean.getType() == Item.Image) {%>
                    <a href="showList?type=Image&category=normal&mode=edit" class="button fit <% if(request.getParameter("category").equals("normal")) {%>special<% } %>">图片</a>
                    <a href="showList?type=Image&category=chart&mode=edit" class="button fit <% if(request.getParameter("category").equals("chart")) {%>special<% } %>">贴图</a>
                    <%} else {%>
                    <% Item type = showListBean.getType(); %>
                    <a href="showList?type=Brand" class="button fit <% if(type == Item.Brand || type == Item.Model || type == Item.Shell) {%>special<% } %>">手机壳</a>
                    <a href="showList?type=TShirt" class="button fit <% if(type == Item.TShirt) {%>special<% } %>">T恤</a>
                    <a href="showList?type=Lighter" class="button fit <% if(type == Item.Lighter) {%>special<% } %>">打火机</a>
                    <%String backUrl;
                    switch (type) {
                        case Model:
                            backUrl = "showList?type=Brand";
                            break;
                        case Shell:
                            backUrl = "showList?type=Model&brand="+request.getParameter("brand");
                            break;
                        default:
                            backUrl = null;
                    }%>
                    <%if(backUrl != null) {%><a href="<%=backUrl%>" class="button fit">返回上层目录</a><% } %>
                    <%}%>
                </div>
            </div>
        </div>
        <div id="actions" class="panel">
            <nav>
                <ul>
                    <li><a id="access" href="#"<%if(showListBean.getType() == Item.Shell) {%> class="add"<%}%>>访问</a></li>
                    <%if(showListBean.getType() == Item.Model) {%>
                        <li><a id="add" href="#">添加</a></li>
                    <%}%>
                    <li><a id="delete" href="#">删除</a></li>
                    <%if(showListBean.getType() == Item.Brand || showListBean.getType() == Item.Model || showListBean.getType() == Item.Image){%>
                    <li><a id="up" href="#">上移</a></li>
                    <li><a id="down" href="#">下移</a></li>
                    <%}%>
                </ul>
            </nav>
        </div>
        <script>
            $('.image').on('click' ,function () {
                var $thumb = $(this).parent();
                var id = $thumb.find('input[name=id]').val();
                var rank = $thumb.find('input[name=rank]').val();
                var accessStr = $thumb.find('input[name=accessStr]').val();
                var addStr = $thumb.find('input[name=addStr]').val();
                $('#access').attr('href', accessStr);
                $('#add').attr('href', addStr);
                $('#up').attr('href', "/rank?<%=queryString%>&oper=up&rank="+ rank);
                $('#down').attr('href', "/rank?<%=queryString%>&oper=down&rank="+ rank);
                $('#delete').attr('href', "/delete?<%=queryString%>&id=" + id);
            })
        </script>
        <!-- Footer -->
        <footer id="search" class="panel">
            <form method="post" class="row" action="search?type=<%=showListBean.getType()%>">
                <input type="hidden" name="type" value="<%=showListBean.getType()%>">
                <div class="field first 10u 12u$(small)">
                    <input type="text" placeholder="<%if(showListBean.getType() != Item.Brand && showListBean.getType() != Item.Model) {%>请输入要搜索的项目ID<%} else {%>请输入要搜索的项目名字<%}%>" name="query">
                </div>
                <div class="field 2u$ 12u$(small)">
                    <input type="submit" value="确定" class="special" />
                </div>
            </form>
        </footer>
        <footer id="footer" class="panel">
            <% if(adminBean != null && adminBean.isStatus() && adminBean.isAuthority()) {%>
            <hr>
            <div class="inner row">
                <div class="6u 12u$(small)">
                    <section>
                        <h2 class="greetings"></h2>
                        <p>欢迎使用超级管理员系统</p>
                    </section>

                    <div class="6u 12u$(small)">
                        <a href="../upload.jsp?type=Brand" class="button fit special">添加品牌</a>
                        <a href="showList?type=Brand" class="button fit special">菜单管理</a>
                        <a href="showList?type=image&category=normal&mode=edit" class="button fit special">图库管理</a>
                    </div>
                    <hr>
                    <ul class="actions">
                        <li><a href="showList?type=Admin" class="button">返回管理员界面</a> <a href="logout" class="button">退出登录</a></li>
                    </ul>
                </div>
                <div class="6u$ 12u$(small)">
                    <h2>更改密码</h2>
                    <form method="post" action="edit?editType=changePassword">
                        <div class="field first">
                            <input name="oldPasswd" type="password" placeholder="请输入旧密码">
                        </div>
                        <div class="field">
                            <input name="password" type="password" placeholder="请输入新密码">
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
            <%} else if(adminBean != null && adminBean.isStatus()) {%>
            <div class="inner row">
                <div class="6u 12u$(small)">
                    <section>
                        <h2 class="greetings"></h2>
                        <p>欢迎使用管理员系统<br>
                            请选择所要进行的操作</p>
                    </section>
                    <section>
                        <h2>会员到期日:<%=adminBean.getExpiration()%></h2>
                    </section>
                    <div class="6u 12u$(small)">
                        <a href="showList?type=Brand" class="button fit special">用户菜单</a>
                        <a href="showList?type=Shell" class="button fit special">作品管理</a>
                    </div>
                    <hr>
                    <ul class="actions">
                        <li><a href="https://api.qrserver.com/v1/create-qr-code/?size=500x500&data=http%3A%2F%2Fwww.macsnow.cn%3A8080%2FshowList%3Ftype%3DBrand%26uid%3D<%=adminBean.getId()%>" class="button">下载二维码</a></li>
                        <li><a href="logout" class="button">退出登录</a></li>
                    </ul>
                </div>
                <div class="3u 12u$(small)">
                    <h2>更改密码</h2>
                    <form method="post" id="changePasswd" action="edit?editType=changePassword">
                        <div class="field first">
                            <input name="oldPasswd" type="password" placeholder="请输入旧密码">
                        </div>
                        <div class="field">
                            <input name="password" id="password" type="password" placeholder="请输入新密码">
                        </div>
                        <div class="field">
                            <input name="confirm" id="confirm" type="password" placeholder="请确认新密码">
                        </div>
                        <ul class="actions">
                            <li>
                                <input type="submit" class="special" value="确定">
                                <input type="reset" value="重置">
                            </li>
                        </ul>
                    </form>
                </div>
                <div class="3u$ 12u$(small)">
                    <h2>编辑信息</h2>
                    <form method="post" action="edit?editType=updateInfo">
                        <div class="field first">
                            <input name="email" type="email" placeholder="请输入您的邮箱">
                        </div>
                        <div class="field">
                            <input name="tel" type="text" placeholder="请输入您的电话号码">
                        </div>
                        <div class="field">
                            <input name="info" type="text" placeholder="请输入您的店铺信息">
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
            <%} else {%>
            <ul class="actions">
                <li><a href="../login.jsp" data-poptrox="iframe,600x500" class="auth button special">登陆</a></li>
                <li><a href="../signup.jsp" data-poptrox="iframe,600x500" class="auth button">注册</a></li>
            </ul>
            <%}%>
        </footer>
    </div>
    <!-- Scripts -->
    <script src="../assets/js/jquery.poptrox.min.js"></script>
    <script src="../assets/js/skel.min.js"></script>
    <script src="../assets/js/util.js"></script>
    <!--[if lte IE 8]><script src="../assets/js/ie/respond.min.js"></script><![endif]-->
    <%--<script src="../assets/js/increase.js"></script>--%>
    <script src="../assets/js/main.js"></script>
</body>

</html>
