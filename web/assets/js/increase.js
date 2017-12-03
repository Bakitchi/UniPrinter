/**
 * Created by michael on 16-10-26.
 */
$(document).ready(function () {
    var $closer = $('.closer');
    $closer.on('click', function (event) {
        $('.closer').parent().hide()
    })
    var now = (new Date()).getHours();
    var greetings
    if (now > 0 && now <= 6) {
        greetings = "午夜好";
    } else if (now > 6 && now <= 11) {
        greetings = "早上好";
    } else if (now > 11 && now <= 14) {
        greetings = "中午好";
    } else if (now > 14 && now <= 18) {
        greetings = "下午好";
    } else {
        greetings = "晚上好";
    }
    $('.greetings').text(greetings);

    $('#signup').validate({
        rules: {
            password: {
                minlength: 6,
                maxlength: 16
            },
            confirm: {
                minlength: 6,
                maxlength: 16,
                equalTo: "#password"
            }
        }
    });
    $('#changePasswd').validate({
        rules: {
            password: {
                minlength: 6,
                maxlength: 16
            },
            confirm: {
                minlength: 6,
                maxlength: 16,
                equalTo: "#password"
            }
        }
    });
    $('.dangerousZone').on('click', function () {
        return confirm("你确定吗？");
    });
})