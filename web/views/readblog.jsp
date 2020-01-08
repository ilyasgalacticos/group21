<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <title>Title</title>
        <%@include file="templates/head.jsp"%>
        <style>
            img{
                width: 100%;
            }
        </style>
    </head>
    <body>
        <%@include file="templates/navbar.jsp"%>
        <div class="container mt-5">
            <div class="row">
                <div class="col-sm-12">
                    <div class="jumbotron">
                        <h1 class="display-4">${blog.title}</h1>
                        <p class="lead">${blog.shortContent}</p>
                        <hr class="my-4">
                        <p class="lead">${blog.content}</p>
                        <p>Posted by : <b>${blog.user.fullName}</b></p>
                        <c:choose>
                            <c:when test="${currentUser!=null&&blog.user.id==currentUser.id}">
                                <a href="/?act=editblog&id=${blog.id}" class="btn btn-success btn-sm">EDIT BLOG</a>
                            </c:when>
                        </c:choose>
                    </div>
                </div>
            </div>
        </div>

        <%@include file="templates/footer.jsp"%>
    </body>
</html>
