/**
 * Created by michael on 16-11-5.
 */

$(document).ready(function () {
    var canvas = new fabric.Canvas('myProduct',{ preserveObjectStacking: true });
    var model = $('#model').val();
    var scale = parseFloat(model.slice(model.length-4));
    // var height = (window.innerWidth > window.innerHeight) ? window.innerHeight*0.9 : window.innerWidth;
    var width;
    var height;
    var trueHeight;
    var trueWidth;
    var coverImage;
    if (window.innerHeight/window.innerWidth > scale) {
        width = window.innerWidth;
        height = width * scale;
    } else {
        height = window.innerHeight*0.9
        width = height/scale
    }
    canvas.setHeight(height);
    canvas.setWidth(width);

    $('#confirm').on('click', function () {
        var color = "#"+$('#selector').val();
        var text = new fabric.IText("请点击这里自定义文字", {
            fontSize: 20,
            fontWeight: 'normal',
            top: height/2,
            left: width/4,
            fill: color
        });
        canvas.add(text);
        text.globalCompositeOperation = 'source-atop';
        canvas.renderAll();
        $('.panel').trigger('---hide');

        // canvas.renderAll();
    });
    $('#delete').on('click', function () {
        var activeObject = canvas.getActiveObject();
        if(activeObject && confirm('你确定吗?')) {
            canvas.remove(activeObject);
        }
    });

    function setStyle(object, styleName, value) {
        if (object.setSelectionStyles) {
            var style = { };
            style[styleName] = value;
            object.selectAll();
            object.setSelectionStyles(style);
        }
        else {
            object[styleName] = value;
        }
    }

    function getStyle(object, styleName) {
        return (object.getSelectionStyles && object.isEditing)
            ? object.getSelectionStyles()[styleName]
            : object[styleName];
    }

    function addHandler(id, fn, eventName) {
        document.getElementById(id)[eventName || 'onclick'] = function() {
            var el = this;
            if (obj = canvas.getActiveObject()) {
                fn.call(el, obj);
                canvas.renderAll();
            }
        };
    }

    addHandler('selector', function(obj) {
        setStyle(obj, 'fill', "#"+$('#selector').val());
    }, "onchange");

    $('#colorSelect').on('click', function () {
        $('#selector').trigger("click");
    });


    $('#image').on('change', function (e) {
        var reader = new FileReader();
        reader.onload = function (event) {
            var imgObj = new Image();
            imgObj.src = event.target.result;
            imgObj.onload = function () {
                // start fabricJS stuff

                var image = new fabric.Image(imgObj);
                image.scale(0.5).set({
                    left: 0,
                    top: 250,
                    angle: 0,
                    minScaleLimit: 0.1
                });
                //image.scale(getRandomNum(0.1, 0.25)).setCoords();
                image.on('selected', function () {
                    console.log('selected');
                });
                canvas.add(image);
                image.globalCompositeOperation = 'source-atop';
                canvas.renderAll();

            }
        };
        reader.readAsDataURL(e.target.files[0]);
        $('.panel').trigger('---hide');
    });
    // if(type != "Brand" && type != "Model")

    canvas.setOverlayImage('images/model/'+model.slice(0, model.length-5)+".png", canvas.renderAll.bind(canvas), {
        left: 0,
        top: 0,
        width: canvas.width,
        height: canvas.height,
        originX: 'left',
        originY: 'top'
    });

    fabric.Image.fromURL('images/model/'+model.slice(0, model.length-5)+"-cover.png",
        function (img) {
            trueHeight = img.height;
            trueWidth = img.width;
            coverImage = img;
            coverImage.set({
                left: 0,
                top: 0,
                scaleY: canvas.height / img.height,
                scaleX: canvas.width / img.width,
                selectable: false
            });
            canvas.add(coverImage);

            canvas.renderAll();
        });
    $('#bank').on('click', function () {
        $.get('showList', {type: "image", category: "normal", mode: "show"}, function (data) {
            $('#main').html(data);
            $('.image').each(function () {
                var $this = $(this)
                $this.on('click', function (event) {
                    fabric.Image.fromURL($this.find('img').attr('src'), function (img) {
                        var img = img.scale(0.5).set({
                            left: 0,
                            top: 250,
                            angle: 0,
                            minScaleLimit: 0.1
                        });
                        canvas.add(img);
                        img.globalCompositeOperation = 'source-atop';
                        canvas.renderAll();
                        $('.panel').trigger('---hide');
                    });
                });
            });
        })
    });

    $('#chart').on('click', function () {
        $.get('showList', {type: "image", category: "chart"}, function (data) {
            $('#main').html(data);
            $('.image').each(function () {
                var $this = $(this);
                $this.on('click', function (event) {
                    fabric.Image.fromURL($this.find('img').attr('src'), function (img) {
                        var img = img.scale(0.5).set({
                            left: 0,
                            top: 250,
                            angle: 0,
                            minScaleLimit: 0.1
                        });
                        img.on('selected', function () {
                            console.log('selected');
                        });
                        canvas.add(img);
                        img.globalCompositeOperation = 'source-atop';
                        canvas.renderAll();
                        $('.panel').trigger('---hide');
                    });
                });
            });
        })
    });

    $('#submit').on('click', function () {
        if(confirm('你确定吗?')) {
            // Generate the image data
            canvas.overlayImage = null;
            canvas.renderAll();
            canvas.isDrawingMode = false;
            // var Pic = canvas.toDataURL('png');
            // Pic = Pic.replace(/^data:image\/(png|jpg);base64,/, "");

            // Sending the image data to Server
            var queryString = $('#queryString').val();
            var xhr = new XMLHttpRequest();
            xhr.open("post", "/upload?"+queryString, false);
            var boundary = Math.random().toString().substr(2);
            xhr.setRequestHeader("content-type",
                "multipart/form-data; charset=utf-8; boundary=" + boundary);
            var multipart = "--" + boundary + "\r\n" +
                "Content-Disposition: form-data; name=uploadImage\r\n" +
                "Content-type: image/png\r\n\r\n" +
                canvas.toDataURL('png').replace(/^data:image\/(png|jpg);base64,/, "") + "\r\n" +
                "--" + boundary + "\r\n" +
                "Content-Disposition: form-data; name=realWidth\r\n" +
                "Content-type: text/html\r\n\r\n" +
                trueWidth + "\r\n" +
                "--" + boundary + "--\r\n";
            xhr.onreadystatechange = function () {
                response = this.responseText.trim();
                var newDoc = document.open("text/html", "status");
                newDoc.write(response);
                newDoc.close();
            };
            xhr.send(multipart);
        }
    });
});