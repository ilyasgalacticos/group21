package com.bitlab.project.servlets;

import com.bitlab.project.db.DBConnection;
import com.bitlab.project.entities.Users;
import com.bitlab.project.repositories.UserRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(value = "/ajax")
public class AjaxServlet extends HttpServlet {

    private UserRepository userRepository;

    public void init(){
        userRepository = new UserRepository();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String act = request.getParameter("act");
        PrintWriter out = response.getWriter();

        if(act.equals("check_user")){

            String email = request.getParameter("email");
            Users user = userRepository.getUser(email);
            if(user!=null){
                out.print("{\"message\":\"User with email: \'"+email+"\' exists!!!\", \"status\":\"error\"}");
            }else{
                out.print("{\"message\":\"User with email: \'"+email+"\' doesn't exists!!! \", \"status\":\"ok\"}");
            }

        }else if(act.equals("login_user")){

            String email = request.getParameter("email");
            String pass = request.getParameter("password");
            Users user = userRepository.getUser(email);

            if(user!=null){
                if(pass!=null&&user.getPassword().trim().equals(pass)){
                    out.print("{\"message\":\"User with email: \'"+email+"\' exists!\", \"status\":\"ok\"}");
                }else{
                    out.print("{\"message\":\"Incorrect password for email: \'"+email+"\'!\", \"status\":\"error\"}");
                }
            }else{
                out.print("{\"message\":\"User with email: \'"+email+"\' doesn't exists! \", \"status\":\"error\"}");
            }

        }else if(act.equals("check_password")){

            Users currentUser = (Users) request.getSession().getAttribute("USER_SESSION");
            if(currentUser!=null){
                String password = request.getParameter("old_pass");
                Users user = userRepository.getUser(currentUser.getEmail());
                if(user.getPassword().equals(password)){

                    out.print("{\"message\":\"Correct password\", \"status\":\"ok\"}");

                }else{

                    out.print("{\"message\":\"Incorrect old password\", \"status\":\"error\"}");

                }
            }

        }
    }
}
