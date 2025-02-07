package com.openclassrooms.mddapi.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Configuration Spring Security *simplifiée* :
 * - /api/login et /api/register sont en accès public.
 * - Toutes les autres routes nécessitent un token JWT valide.
 */
@Configuration
public class SecurityConfig {

    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtUtils jwtUtils;
    private final CustomUserDetailsService userDetailsService;

    public SecurityConfig(
            JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint,
            JwtUtils jwtUtils,
            CustomUserDetailsService userDetailsService
    ) {
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.jwtUtils = jwtUtils;
        this.userDetailsService = userDetailsService;
    }

    /**
     * La configuration principale :
     *  - Désactivation CSRF (pour usage d'API stateless).
     *  - Ajout d’un filtre JWT *avant* UsernamePasswordAuthenticationFilter.
     *  - Autorisation en clair pour /api/login, /api/register et /error.
     *  - Toutes les autres URL sont protégées.
     *  - Session stateless (pas de session Http).
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                // En cas d'erreur 401 (pas de token ou token invalide),
                // on renvoie un JSON personnalisé via jwtAuthenticationEntryPoint
                .exceptionHandling(ex -> ex.authenticationEntryPoint(jwtAuthenticationEntryPoint))
                // Stateless: on n'utilise pas de session Http (tout se fait par token)
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        // Autorise toute requête sur /error (sinon /api/register qui plante
                        // peut rediriger vers /error, et tu te retrouves 401 sur /error)
                        .requestMatchers("/error").permitAll()
                        // Autorise POST sur /api/register et /api/login
                        .requestMatchers(HttpMethod.POST, "/api/register").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/login").permitAll()
                        // Autorise si tu veux GET sur /api/hello
                        .requestMatchers("/api/hello").permitAll()
                        // Tout le reste requiert authentification
                        .anyRequest().authenticated()
                )
                // On ajoute le filtre JWT
                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /**
     * Filtre JWT : cherche un token dans l'en-tête Authorization
     * et si présent/valide, authentifie l’utilisateur dans le SecurityContext.
     */
    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter(jwtUtils, userDetailsService);
    }

    /**
     * Permet à Spring d'obtenir l'AuthenticationManager pour faire l'authentification
     * (utilisé par AuthService pour authentifier via login).
     */
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration
    ) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * Encodeur de mot de passe (BCrypt).
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
