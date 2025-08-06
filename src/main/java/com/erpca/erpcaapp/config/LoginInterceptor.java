// src/main/java/com/erpca/erpcaapp/config/LoginInterceptor.java
package com.erpca.erpcaapp.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        HttpSession session = request.getSession(false);
        // If no session or no LOGGED_IN_USER attribute, redirect to /login
        if (session == null || session.getAttribute("LOGGED_IN_USER") == null) {
            // Preserve the requested URI (optional):
            String uri = request.getRequestURI();
            // If it’s already /login or /logout or static CSS/JS, allow it
            if (uri.startsWith("/login") || uri.startsWith("/logout")
                || uri.startsWith("/css/") || uri.startsWith("/js/")) {
                return true;
            }

            // Otherwise redirect to /login
            response.sendRedirect(request.getContextPath() + "/login");
            return false;
        }
        // User is logged in, allow the request to proceed
        return true;
    }
}
