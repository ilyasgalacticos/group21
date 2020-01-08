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
                    <c:forEach items="${blogs}" var="blog">
                        <div class="jumbotron">
                            <h2>${blog.title}</h2>
                            <p class="lead">${blog.shortContent}</p>
                            <hr class="my-4">
                            <p>Posted by : <b>${blog.user.fullName}</b></p>
                            <a class="btn btn-primary btn-sm" href="/?act=readblog&id=${blog.id}" role="button">Read more</a>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </div>

        <%@include file="templates/footer.jsp"%>
    </body>
</html>
