package com.hsbug.backend.app.user_register;


import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Slf4j
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    // accountService;
    UserRegisterDto form;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,HttpServletResponse response,
                Authentication authentication)throws IOException, ServletException {

        HttpSession session = request.getSession();

        log.info("Success Log in = {}", authentication.getName());
        session.setAttribute("username", authentication.getName());
        session.setAttribute("role", authentication.getAuthorities());

        log.info("authentication.getName() = {}",authentication.getName());
        log.info("authentication.getAuthorities() = {}",authentication.getAuthorities());


        response.sendRedirect("/api/loginSuccess");
    }
}
