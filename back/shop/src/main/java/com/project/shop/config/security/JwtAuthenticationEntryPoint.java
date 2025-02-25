package com.project.shop.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        log.error("[Access right exception]", authException);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, PATCH, DELETE");
        String message = authException.getCause() != null ? authException.getCause().getMessage() : authException.getMessage();
        Map<String, String> bodyMap = new HashMap<>();
        bodyMap.put("Status", response.getStatus() + "");
        bodyMap.put("Error", message);
        byte[] body = new ObjectMapper().writeValueAsBytes(bodyMap);
        response.getOutputStream().write(body);
    }

}
