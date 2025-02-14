package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.payload.response.JwtResponse;
import com.openclassrooms.mddapi.payload.request.LoginRequest;
import com.openclassrooms.mddapi.payload.request.SignupRequest;
import com.openclassrooms.mddapi.services.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Contrôleur d'authentification pour gérer les opérations de connexion et d'inscription des utilisateurs.
 * <p>
 * Fournit des endpoints pour l'authentification des utilisateurs et l'inscription via JWT.
 * </p>
 */
@RestController
@RequestMapping("/api")
public class AuthController {

    private final AuthService authService;

    /**
     * Constructeur pour injecter le service d'authentification.
     *
     * @param authService Service d'authentification gérant les connexions et inscriptions.
     */
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    /**
     * Authentifie un utilisateur et génère un token JWT.
     *
     * @param loginRequest Objet contenant l'email et le mot de passe de l'utilisateur.
     * @return ResponseEntity contenant le token JWT et les informations de l'utilisateur.
     */
    @PostMapping("/login")
    public ResponseEntity<JwtResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        JwtResponse jwtResponse = authService.login(loginRequest);
        return ResponseEntity.ok(jwtResponse);
    }

    /**
     * Inscrit un nouvel utilisateur.
     *
     * @param signupRequest Objet contenant les informations d'inscription de l'utilisateur.
     * @return ResponseEntity contenant un message confirmant l'inscription réussie.
     */
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signupRequest) {
        String message = authService.register(signupRequest);
        return ResponseEntity.ok(message);
    }
}
