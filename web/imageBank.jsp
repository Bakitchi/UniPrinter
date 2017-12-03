<%--
    Created by IntelliJ IDEA.
    User: michael
    Date: 17-1-17
    Time: 下午7:23
    To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="showListBean" scope="session" class="JavaBean.ShowListBean"/>

<% if(showListBean.getBeanSet() != null && showListBean.getBeanSet().size() != 0) {
    for (Object itemBean: showListBean.getBeanSet()) { %>
    <article class="thumb">
        <a href="#" class="image"><img src="<%=request.getContextPath() %><%=((JavaBean.ItemBean)itemBean).getInfo().trim()%>" alt="" /></a>
    </article>
    <% } %>
    <%--<%if(showListBean.isHavePrev() || showListBean.isHaveNext()) {%>--%>
    <%--<article class='thumb' id="pageAction">--%>
        <%--<a href="showList?type=image&category=&page=<%=showListBean.getShowPage() + 1%>" id="nextpage"  class="button <% if(!showListBean.isHaveNext()) {%>disabled<% } %>">--%>
            <%--<div>--%>
                <%--<h1>下页</h1>--%>
                <%--<i class="fa fa-chevron-right fa-5x"></i>--%>
            <%--</div>--%>
        <%--</a>--%>
    <%--</article>--%>
    <%--<% } %>--%>
<% } %>