package com.example.capstone.Security.JWT;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class AuthEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        final Map<String, Object> errorsInfo = new HashMap<>();
        errorsInfo.put("state", HttpServletResponse.SC_UNAUTHORIZED);
        errorsInfo.put("error", "Invalid authentication");
        errorsInfo.put("message", authException.getMessage());
        errorsInfo.put("path", request.getServletPath());

        final ObjectMapper errorMapping = new ObjectMapper();
        errorMapping.writeValue(response.getOutputStream(), errorsInfo);
    }
}
