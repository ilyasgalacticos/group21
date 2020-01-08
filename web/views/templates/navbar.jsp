<%@ page import="com.bitlab.project.entities.Users" %><%
    Users user = (Users)request.getAttribute("currentUser");
%>
<nav class="navbar navbar-expand-lg navbar-dark bg-info">
    <a class="navbar-brand" href="/?">Twitter</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
            <%
                if(user!=null){
            %>
            <li class="nav-item">
                <a class="nav-link" href="/?act=profile"><% out.print(user.getFullName()); %></a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/?act=addblog">Add Blog</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="JavaScript:void(0)" onclick="logout()">Logout</a>
            </li>
            <form action="/process" method="post" id = "logoutForm">
                <input type="hidden" value="logout" name="act">
            </form>
            <script>
                function logout(){
                    $("#logoutForm").submit();
                }
            </script>
            <%
                }else{
            %>
            <li class="nav-item">
                <a class="nav-link" href="/">Home</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/?act=register">Register</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/?act=login">Sign In</a>
            </li>
            <%
                }
            %>
        </ul>
    </div>
</nav>