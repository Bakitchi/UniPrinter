<%--
    Created by IntelliJ IDEA.
    User: michael
    Date: 16-11-6
    Time: 下午6:57
    To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="infoBean" scope="session" class="JavaBean.InfoBean"/>
<!DOCTYPE HTML>
<html id="iframe">
<head>
    <title>消息</title>
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
        <h1>上传成功！</h1>
        <p>您的作品编号为：<%=infoBean.getInfo()%></p>
        <a href="showList?<%=request.getQueryString()%>" class="button" >返回</a>
    </div>
</div>

</body>
</html>