<%--
    Created by IntelliJ IDEA.
    User: michael
    Date: 16-11-5
    Time: 下午4:57
    To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>图片编辑</title>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no" />
    <!--[if lte IE 8]><script src="assets/js/ie/html5shiv.js"></script><![endif]-->
    <link rel="stylesheet" href="assets/css/main.css" />
    <link rel="stylesheet" href="assets/css/increase.css" />
    <!--[if lte IE 9]><link rel="stylesheet" href="assets/css/ie9.css" /><![endif]-->
    <!--[if lte IE 8]><link rel="stylesheet" href="assets/css/ie8.css" /><![endif]-->
    <script src="assets/js/jquery.min.js"></script>
    <script src="assets/js/jscolor.min.js"></script>
</head>
<body style="overflow: hidden">
<div id="wrapper">
    <header id="header">
        <%--<h1><strong><span id="closer" style="display: block; cursor: pointer;">Edit</span></strong></h1>--%>
        <nav>
            <ul>
                <li><a href="#" id="submit">提交</a></li>
                <li><a href="#imgAction">图片</a></li>
                <li><a href="#textAction">文字</a></li>
                <li><a href="#" id="delete">删除所选项</a></li>
            </ul>
        </nav>
    </header>
    <div id="canvas-container" class="inner">
        <canvas id="myProduct"></canvas>
    </div>
    <footer id="textAction" class="panel">
        <div class="inner row">
            <div class="12u 12u$(small)">
                <button id="confirm" class="special fit">添加</button>
            </div>
            <div class="12u 12u$(small)">
                <button id="colorSelect" class="special fit jscolor {valueElement:null,valueElement:'selector'}">颜色</button>
                <input id="selector" value="000000">
            </div>
        </div>
    </footer>
    <footer id="imgAction" class="panel">
        <div class="inner row">
            <div class="12u 12u$(small)">
                <button id="upload" class="button fit special">
                    <input id="image" type="file" name="image">
                    上传</button>
            </div>
            <div class="12u 12u$(small)">
                <a href="#images" id="bank" class="button special fit">图库</a>
            </div>
            <div class="12u 12u$(small)">
                <a href="#images" id="chart" class="button special fit">贴纸</a>
            </div>
        </div>
    </footer>
    <footer id="images" class="panel">
        <div class="inner">
            <div id="main">
            </div>
        </div>
    </footer>
</div>
<input type="hidden" id="queryString" value="<%=request.getQueryString()%>">
<input type="hidden" id="model" value="<%=request.getParameter("model")%>">
<%--<input type="hidden" id="type" value="<%=request.getParameter("type")%>">--%>
<script src="assets/js/jquery.poptrox.min.js"></script>
<script src="assets/js/skel.min.js"></script>
<script src="assets/js/util.js"></script>
<!--[if lte IE 8]><script src="assets/js/ie/respond.min.js"></script><![endif]-->
<script src="assets/js/fabric.js"></script>
<script src="assets/js/edit.js"></script>
<script src="assets/js/main.js"></script>
</body>
</html>