package com.example.backend_java.config;

import com.example.backend_java.common.TokenType;
import com.example.backend_java.service.JwtService;
import com.example.backend_java.service.UserServiceDetail;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Date;

@Component
@Slf4j(topic = "CustomRequestFillter")
public class CustomRequestFillter extends OncePerRequestFilter {
    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserServiceDetail serviceDetail;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
       log.info("{} {}",request.getMethod(),request.getRequestURI());
       String authheader = request.getHeader("Authorization");
       log.info("header: {}",authheader);
       if (authheader != null && authheader.startsWith("Bearer ")) {
           authheader = authheader.substring(7);
           log.info("authToken: {}",authheader.substring(0,20));
           String username = "";
           try {
               username = jwtService.extractUserName(authheader, TokenType.ACCESS_TOKEN);
               log.info("username: {}", username);
           } catch (AccessDeniedException e) {
               log.info(e.getMessage());
               response.setStatus(HttpServletResponse.SC_OK);
               response.getWriter().write(errorResponse(e.getMessage()));
               return;
           }
           UserDetails user = serviceDetail.UserServiceDetail().loadUserByUsername(username);
           log.info("user: {}", user);

           SecurityContext context = SecurityContextHolder.createEmptyContext();
           log.info("context: {}", context);
           UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
           authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
           context.setAuthentication(authToken);
           SecurityContextHolder.setContext(context);

           filterChain.doFilter(request, response);
       } else {
           filterChain.doFilter(request, response);
       }
       }
    private String errorResponse(String message) {
        try {
            ErrorResponse error = new ErrorResponse();
            error.setTimestamp(new Date());
            error.setError("Forbidden");
            error.setStatus(HttpServletResponse.SC_FORBIDDEN);
            error.setMessage(message);

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            return gson.toJson(error);
        } catch (Exception e) {
            return ""; // Return an empty string if serialization fails
        }
    }
    @Setter
    @Getter
    private class ErrorResponse {
        private Date timestamp;
        private int status;
        private String error;
        private String message;
    }

}
