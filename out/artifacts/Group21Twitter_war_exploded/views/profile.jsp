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
            <form action="/process" method="post" id = "password_form">
                <input type="hidden" name="act" value="update_password">
                <div class="form-group">
                    <label>Old Password</label>
                    <input type="password" class="form-control" id = "old_password" name="old_password">
                </div>
                <div class="form-group">
                    <label>New Password</label>
                    <input type="password" class="form-control" id = "new_password" name="new_password">
                </div>
                <div class="form-group">
                    <label>Confirm New Password</label>
                    <input type="password" class="form-control" id = "re_password" name="re_password">
                </div>
                <button type="button" class="btn btn-primary" onclick="updatePassword()">Update Password</button>
            </form>
            <form action="/process" method="post" id = "profile_form">
                <input type="hidden" name="act" value="update_profile">
                <div class="form-group">
                    <label>Full Name</label>
                    <input type="text" class="form-control" id = "full_name" name="full_name" value="${currentUser.fullName}">
                </div>
                <button type="button" class="btn btn-primary" onclick="updateProfile()">Update Profile</button>
            </form>
        </div>
    </div>
</div>
<script type="text/javascript">

    function validatePasswordForm() {

        old_password = $("#old_password").val(); // document.getElementById("old_password");
        new_password = $("#new_password").val();
        re_password = $("#re_password").val();

        return old_password.length>=6&&new_password.length>=6&&re_password.length>=6;

    }

    function validateProfileForm() {

        full_name = $("#full_name").val();

        return full_name.length>0;

    }

    function updateProfile(){

        if(validateProfileForm()){

            $("#profile_form").submit();

        }else{
            $("#message_text").html("Complete full name form!");
            $("#message").fadeIn();
        }
    }

    function updatePassword(){

        if(validatePasswordForm()){

            if($("#new_password").val()===$("#re_password").val()){

                $.get("/ajax", {

                    "old_pass": $("#old_password").val(),
                    "act": "check_password"

                }, function (result) {

                    res = JSON.parse(result);

                    if(res.status==="ok"){

                        $("#password_form").submit();

                    }else{
                        $("#message_text").html(res.message);
                        $("#message").fadeIn();
                    }

                });

            }else{

                $("#message_text").html("New passwords are not same!");
                $("#message").fadeIn();

            }

        }else{
            $("#message_text").html("Complete password form!");
            $("#message").fadeIn();
        }

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
