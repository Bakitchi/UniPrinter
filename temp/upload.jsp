<%--
    Created by IntelliJ IDEA.
    User: michael
    Date: 16-11-6
    Time: 下午6:05
    To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="showListBean" scope="session" class="JavaBean.ShowListBean"/>
<html>
<head>
    <title>upload</title>
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
            <div class="12u inner row">
                <%--<div class="4u 12u$(small)">asd</div>--%>
                <div class="4u$ 12u$(small)">
                    <section>
                        <h2>upload</h2>
                        <section>
                            <form id="upload" method="post" action="upload" enctype="multipart/form-data">
                                <div class="field first">
                                    <label>Name</label>
                                    <input type="text" name="name" placeholder="Name" />
                                </div>
                                <div class="field">
                                    <label>image</label>
                                    <input type="file" name="image">
                                </div>
                                <input type="hidden" name="type" value="<%=showListBean.getType()%>">
                                <ul class="actions">
                                    <li><button type="submit" value="Submit" class="special">submit</button></li>
                                    <li><input type="reset" value="Reset" /></li>
                                </ul>
                            </form>
                        </section>
                    </section>
                </div>
                <%--<div class="4u 12u$(small)"></div>--%>
            </div>
        </div>
    </div>
</body>
</html>
