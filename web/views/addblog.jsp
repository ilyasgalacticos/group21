<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <%@include file="templates/head.jsp"%>
    <script src="https://cdn.ckeditor.com/ckeditor5/16.0.0/classic/ckeditor.js"></script>
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
            <form action="/process" method="post">
                <input type="hidden" name="act" value="addblog">
                <div class="form-group">
                    <label>Title</label>
                    <input type="text" class="form-control" name="title">
                </div>
                <div class="form-group">
                    <label>Short Content</label>
                    <textarea name="short_content" id = "short_content_editor"></textarea>
                </div>
                <div class="form-group">
                    <label>Content</label>
                    <textarea name="content" id = "content_editor"></textarea>
                </div>
                <button class="btn btn-primary">Add Blog</button>
            </form>
            <script>
                ClassicEditor
                    .create( document.querySelector( '#short_content_editor' ) )
                    .catch( error => {
                    console.error(error);
                } );

                ClassicEditor
                    .create( document.querySelector( '#content_editor' ) )
                    .catch( error => {
                        console.error(error);
                    });
            </script>
        </div>
    </div>
</div>

<%@include file="templates/footer.jsp"%>
</body>
</html>
