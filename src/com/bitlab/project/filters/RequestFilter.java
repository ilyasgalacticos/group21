package com.bitlab.project.filters;

import com.bitlab.project.entities.Users;
import com.bitlab.project.repositories.UserRepository;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(value = "/*")
public class RequestFilter implements Filter {

    private UserRepository userRepository;

    public void destroy() {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {

        HttpSession session = ((HttpServletRequest)request).getSession();
        boolean online = false;
        Users user = (Users) session.getAttribute("USER_SESSION");

        if(user!=null){
            Users currentUser = userRepository.getUser(user.getEmail());
            if(currentUser!=null&&currentUser.getPassword().equals(user.getPassword())){
                online = true;
                request.setAttribute("CURRENT_USER", currentUser);
            }else{
                session.removeAttribute("USER_SESSION");
            }
        }

        request.setAttribute("USER_ONLINE", online);
        chain.doFilter(request, response);

    }

    public void init(FilterConfig config) throws ServletException {
        userRepository = new UserRepository();
    }

}
