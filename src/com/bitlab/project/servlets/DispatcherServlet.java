package com.bitlab.project.servlets;

import com.bitlab.project.db.DBConnection;
import com.bitlab.project.entities.Blogs;
import com.bitlab.project.entities.Users;
import com.bitlab.project.repositories.BlogRepository;
import com.bitlab.project.repositories.UserRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

@WebServlet(value = "/process")
public class DispatcherServlet extends HttpServlet {

    private UserRepository userRepository;
    private BlogRepository blogRepository;

    public void init(){
        userRepository = new UserRepository();
        blogRepository = new BlogRepository();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String act = request.getParameter("act");
        String redirect = "/";
        Users userSession = (Users)request.getSession().getAttribute("USER_SESSION");

        if(act!=null){
            if(act.equals("register")&&userSession==null){

                redirect = "/?act=register&error";

                String email = request.getParameter("email");
                String password = request.getParameter("password");
                String fullName = request.getParameter("full_name");

                if(
                    email!=null&&!email.trim().equals("")&&
                    password!=null&&password.trim().length()>=6&&
                    fullName!=null&&!fullName.trim().equals("")
                ){
                    if(userRepository.addUser(new Users(null, email, password, fullName))){
                        redirect = "/?act=register&success";
                    }
                }

            }else if(act.equals("login")&&userSession==null){

                redirect = "/?act=login&error";

                String email = request.getParameter("email");
                String password = request.getParameter("password");

                Users user = userRepository.getUser(email);
                if(user!=null){
                    if(user.getPassword().equals(password)){
                        request.getSession().setAttribute("USER_SESSION", user);
                        redirect = "/?";
                    }
                }

            }else if(act.equals("logout")&&userSession!=null){

                request.getSession().removeAttribute("USER_SESSION");
                redirect = "/?act=login";

            }else if(act.equals("addblog")&&userSession!=null){

                String title = request.getParameter("title");
                String shortContent = request.getParameter("short_content");
                String content = request.getParameter("content");

                Blogs blog = new Blogs(null, userSession, title, shortContent, content, new Date());

                blogRepository.addBlog(blog);
                redirect = "/?act=addblog&success";

            }else if(act.equals("update_password")&&userSession!=null){

                String oldPassword = request.getParameter("old_password");
                String newPassword = request.getParameter("new_password");
                String reNewPassword = request.getParameter("re_password");

                Users user = userRepository.getUser(userSession.getEmail());

                if(reNewPassword.equals(newPassword)&&user!=null&&user.getPassword().equals(oldPassword)){
                    if(userRepository.updatePassword(new Users(userSession.getId(), null, newPassword, null))){
                        request.getSession().setAttribute("USER_SESSION", userRepository.getUser(userSession.getEmail()));
                        redirect = "/?act=profile&success";
                    }
                }
            }else if(act.equals("update_profile")&&userSession!=null){
                String fullName = request.getParameter("full_name");
                if(userRepository.updateUser(new Users(userSession.getId(), null, null, fullName))){
                    redirect = "/?act=profile&success";
                }
            }else if(act.equals("saveblog")&&userSession!=null){

                Long id = Long.parseLong(request.getParameter("id"));
                String title = request.getParameter("title");
                String shortContent = request.getParameter("short_content");
                String content = request.getParameter("content");

                Blogs blog = new Blogs(id, userSession, title, shortContent, content, new Date());

                blogRepository.updateBlog(blog);
                redirect = "/?act=readblog&id="+id;

            }else if(act.equals("deleteblog")&&userSession!=null){

                Long id = Long.parseLong(request.getParameter("id"));

                blogRepository.deleteBlog(id, userSession.getId());
                redirect = "/?";

            }
        }

        response.sendRedirect(redirect);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String act = request.getParameter("act");
        String jspPage = "index";
        ArrayList<Blogs> blogs = blogRepository.getAllBlogs();
        request.setAttribute("blogs", blogs);

        Users currentUser = (Users)request.getAttribute("CURRENT_USER");
        boolean online = (boolean)request.getAttribute("USER_ONLINE");

        request.setAttribute("currentUser", currentUser);

        if(act!=null){
            if(act.equals("index")){
                jspPage = "index";
            }else if(act.equals("register")){
                jspPage = "register";
            }else if(act.equals("login")){
                jspPage = "login";
            }else if(act.equals("profile")&&online){
                jspPage = "profile";
            }else if(act.equals("addblog")&&online){
                jspPage = "addblog";
            }else if(act.equals("readblog")){

                Long id = Long.parseLong(request.getParameter("id"));
                Blogs blog = blogRepository.getBlog(id);
                request.setAttribute("blog", blog);
                jspPage = "readblog";

            }else if(act.equals("editblog")&&online){

                Long id = Long.parseLong(request.getParameter("id"));
                Blogs blog = blogRepository.getBlog(id);

                if(blog!=null&&currentUser.getId()==blog.getUser().getId()){
                    request.setAttribute("blog", blog);
                    jspPage = "editblog";
                }
            }
        }

        request.getRequestDispatcher("/views/"+jspPage+".jsp").forward(request, response);

    }
}
