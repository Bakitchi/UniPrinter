<%@ page import="JavaBean.Item" %><%--
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
    <script src="assets/js/jquery.min.js"></script>
</head>
<body>
    <div id="wrapper">
        <div class="container">
            <div class="12u inner row">
                <%--<div class="4u 12u$(small)">asd</div>--%>
                <div class="4u$ 12u$(small)">
                    <section>
                        <h2>上传图片</h2>
                        <h3>只能上传后缀名为.png的图片</h3>
                        <section>
                            <form id="upload" method="post" enctype="multipart/form-data">
                                <div class="field first">
                                    <label>名称</label>
                                    <input type="text" name="name" id="name" placeholder="名称" >
                                </div>
                                <div class="field">
                                    <label>模板图</label>
                                    <input type="file" name="image" id="uploadImage" onchange="getuploadImage()">
                                </div>
                                <%if(showListBean.getType() == Item.Model) {%>
                                <div class="field">
                                    <label>封面图</label>
                                    <input type="file" name="image" id="showImage" onchange="getshowImage()">
                                </div>
                                <div class="field">
                                    <label>覆盖图</label>
                                    <input type="file" name="image" id="coverImage" onchange="getcoverImage()">
                                </div>
                                <%}%>
                                <%--<input type="hidden" name="type" value="<%=showListBean.getType()%>">--%>
                                <ul class="actions">
                                    <li><button id="submit" class="special">上传</button></li>
                                    <li><button type="reset" >重置</button></li>
                                    <li><a href="showList?<%=request.getQueryString()%>" class="button" >返回</a></li>
                                </ul>
                            </form>
                        </section>
                    </section>
                </div>
                <%--<div class="4u 12u$(small)"></div>--%>
            </div>
        </div>
    </div>
    <input id="type" type="hidden" value="<%=request.getParameter("type")%>">
    <script>
        var uploadImage;
        var showImage;
        var coverImage;
        var scale;
        function getuploadImage() {
            var file    = $('#uploadImage')[0].files[0];
            var reader  = new FileReader();

            reader.addEventListener("load", function () {
                uploadImage = reader.result;
                var img = new Image();
                img.onload=function(){
                    var width = img.width;
                    var height = img.height;
//                    scale = (height/width).toFixed(2);
                };
                img.src= uploadImage;
            }, false);
            if (file) {
                reader.readAsDataURL(file);
            }
        }
        function getshowImage() {
            var file    = $('#showImage')[0].files[0];
            var reader  = new FileReader();

            reader.addEventListener("load", function () {
                showImage = reader.result;
                var img = new Image();
                img.onload=function(){
                    var width = img.width;
                    var height = img.height;
//                    scale = (height/width).toFixed(2);
                };
                img.src= showImage;
            }, false);
            if (file) {
                reader.readAsDataURL(file);
            }
        }
        function getcoverImage() {
            var file    = $('#coverImage')[0].files[0];
            var reader  = new FileReader();

            reader.addEventListener("load", function () {
                coverImage = reader.result;
                var img = new Image();
                img.onload=function(){
                    var width = img.width;
                    var height = img.height;
                    scale = (height/width).toFixed(2);
                };
                img.src= coverImage;
            }, false);
            if (file) {
                reader.readAsDataURL(file);
            }
        }
        $('#submit').on('click', function () {
            if(confirm('你确定吗?')) {
                // Generate the image data

                // Sending the image data to Server
                var xhr = new XMLHttpRequest();
                xhr.open("post", "/upload?<%=request.getQueryString()%>", false);
                var name = $('#name').val();
                var boundary = Math.random().toString().substr(2);
                xhr.setRequestHeader("content-type",
                        "multipart/form-data; charset=utf-8; boundary=" + boundary);

                <%if(showListBean.getType() == Item.Model) {%>
                    var multipart = "--" + boundary + "\r\n" +
                            "Content-Disposition: form-data; name=uploadImage\r\n" +
                            "Content-type: image/png\r\n\r\n" +
                            uploadImage.replace(/^data:image\/(png|jpg);base64,/, "") + "\r\n" +
                            "--" + boundary + "\r\n" +
                            "Content-Disposition: form-data; name=showImage\r\n" +
                            "Content-type: image/png\r\n\r\n" +
                            showImage.replace(/^data:image\/(png|jpg);base64,/, "") + "\r\n" +
                            "--" + boundary + "\r\n" +
                            "Content-Disposition: form-data; name=coverImage\r\n" +
                            "Content-type: image/png\r\n\r\n" +
                            coverImage.replace(/^data:image\/(png|jpg);base64,/, "") + "\r\n" +
                            "--" + boundary + "\r\n" +
                            "Content-Disposition: form-data; name=name\r\n" +
                            "Content-type: text/html\r\n\r\n" +
                            name + "\r\n" +
                            "--" + boundary + "\r\n" +
                            "Content-Disposition: form-data; name=scale\r\n" +
                            "Content-type: text/html\r\n\r\n" +
                            scale + "\r\n" +
                            "--" + boundary + "--\r\n";
                <%} else {%>
                    var multipart = "--" + boundary + "\r\n" +
                            "Content-Disposition: form-data; name=uploadImage\r\n" +
                            "Content-type: image/png\r\n\r\n" +
                            uploadImage.replace(/^data:image\/(png|jpg);base64,/, "") + "\r\n" +
                            "--" + boundary + "\r\n" +
                            "Content-Disposition: form-data; name=name\r\n" +
                            "Content-type: text/html\r\n\r\n" +
                            name + "\r\n" +
                            "--" + boundary + "--\r\n";
                <%}%>
//                xhr.onreadystatechange = function () {
//                    response = this.responseText.trim();
//                    var newDoc = document.open("text/html", "status");
//                    newDoc.write(response);
//                    newDoc.close();
//                }
                xhr.send(multipart);
            }
        });
    </script>
</body>
</html>
