<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Title</title>
        <%@include file="templates/head.jsp"%>
    </head>
    <body>
        <%@include file="templates/navbar.jsp"%>
        <div class="container mt-5">
            <div class="row">
                <div class="col-sm-12">
                    <div class="alert alert-danger" role="alert" style="display: none;" id = "message">
                        <strong id = "message_text"></strong>
                        <button type="button" class="close" id = "message_close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <form action="/process" method="post" id = "register_form">
                        <input type="hidden" name="act" value="register">
                        <div class="form-group">
                            <label>Email</label>
                            <input type="email" class="form-control" id = "email" name="email">
                        </div>
                        <div class="form-group">
                            <label>Password</label>
                            <input type="password" class="form-control" id = "password" name="password">
                        </div>
                        <div class="form-group">
                            <label>Full Name</label>
                            <input type="text" class="form-control" id = "full_name" name="full_name">
                        </div>
                        <button type="button" class="btn btn-primary" onclick="checkEmail()">Sign In</button>
                    </form>
                </div>
            </div>
        </div>
        <script type="text/javascript">
            
            function validateForm() {

                email = $("#email").val();
                password = $("#password").val();
                full_name = $("#full_name").val();

                return email.length>0&&password.length>=6&&full_name.length>0;

            }

            function checkEmail(){
                email = $("#email").val();

                $.get("/ajax", {
                    "act" : "check_user",
                    "email": email
                }, function (result) {

                    res = JSON.parse(result);
                    if(res.status==="ok"){

                        if(validateForm()){
                            $("#register_form").submit();
                        }else{
                            $("#message_text").html("Complete Form");
                            $("#message").fadeIn();
                        }

                    }else{
                        $("#message_text").html(res.message);
                        $("#message").fadeIn();
                    }
                })
            }
            $(document).ready(function(){
                $("#message_close").click(function(){
                    $("#message").fadeOut();
                });
            });
        </script>
        <%@include file="templates/footer.jsp"%>
    </body>
</html>
