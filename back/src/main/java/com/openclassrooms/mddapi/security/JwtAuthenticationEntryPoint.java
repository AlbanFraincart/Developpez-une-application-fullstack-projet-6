package com.openclassrooms.mddapi.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Classe gérant les erreurs d'authentification dans Spring Security.
 * <p>
 * Cette classe intercepte les accès non autorisés et renvoie une réponse JSON explicite.
 * </p>
 */
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Intercepte les erreurs d'authentification et renvoie une réponse JSON avec le statut et un message explicatif.
     *
     * @param request       La requête HTTP.
     * @param response      La réponse HTTP.
     * @param authException L'exception d'authentification.
     * @throws IOException      En cas d'erreur d'entrée/sortie.
     * @throws ServletException En cas d'erreur liée au servlet.
     */
    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException)
            throws IOException, ServletException {

        response.setContentType("application/json;charset=UTF-8");

        int status = request.getUserPrincipal() == null ? HttpServletResponse.SC_UNAUTHORIZED : HttpServletResponse.SC_FORBIDDEN;

        response.setStatus(status);

        Map<String, Object> body = new HashMap<>();
        body.put("status", status);
        body.put("error", status == 401 ? "Unauthorized" : "Forbidden");
        body.put("message", authException.getMessage());
        body.put("path", request.getServletPath());

        objectMapper.writeValue(response.getOutputStream(), body);
    }
}
