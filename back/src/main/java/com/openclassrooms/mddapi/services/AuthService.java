package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.entities.User;
import com.openclassrooms.mddapi.payload.response.JwtResponse;
import com.openclassrooms.mddapi.payload.request.LoginRequest;
import com.openclassrooms.mddapi.payload.request.SignupRequest;
import com.openclassrooms.mddapi.repositories.UserRepository;
import com.openclassrooms.mddapi.security.JwtUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Service de gestion de l'authentification et de l'inscription des utilisateurs.
 * <p>
 * Ce service permet l'authentification des utilisateurs, la génération de JWT,
 * et la gestion des inscriptions avec stockage sécurisé des mots de passe.
 * </p>
 */
@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;

    /**
     * Constructeur d'AuthService injectant les dépendances nécessaires.
     *
     * @param authenticationManager Gestionnaire d'authentification Spring Security.
     * @param userRepository        Référentiel des utilisateurs.
     * @param jwtUtils              Utilitaire pour la gestion des tokens JWT.
     * @param passwordEncoder       Encodeur de mots de passe.
     */
    public AuthService(AuthenticationManager authenticationManager,
                       UserRepository userRepository,
                       JwtUtils jwtUtils,
                       PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.jwtUtils = jwtUtils;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Authentifie un utilisateur et génère un token JWT en cas de succès.
     *
     * @param loginRequest Objet contenant les informations de connexion (email et mot de passe).
     * @return {@link JwtResponse} contenant le token JWT, le nom d'utilisateur et l'email.
     * @throws RuntimeException si l'utilisateur n'est pas trouvé ou si l'authentification échoue.
     */
    public JwtResponse login(LoginRequest loginRequest) {
        // Authentification de l'utilisateur avec Spring Security
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Génération du token JWT
        String jwt = jwtUtils.generateJwtToken(authentication);

        // Récupération de l'utilisateur en base de données
        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("Erreur : Utilisateur non trouvé."));

        // Retourne la réponse contenant le token JWT et les informations utilisateur
        return new JwtResponse(jwt, user.getUsername(), user.getEmail());
    }

    /**
     * Inscrit un nouvel utilisateur en vérifiant d'abord que l'email n'est pas déjà utilisé.
     *
     * @param signupRequest Objet contenant les informations d'inscription (nom, email, mot de passe, etc.).
     * @return Message confirmant la réussite de l'inscription.
     * @throws RuntimeException si l'email est déjà utilisé.
     */
    public String register(SignupRequest signupRequest) {
        // Vérification de l'existence de l'email
        userRepository.findByEmail(signupRequest.getEmail()).ifPresent(user -> {
            throw new RuntimeException("Erreur : L'email est déjà utilisé !");
        });

        // Création et encodage du mot de passe de l'utilisateur
        User user = User.builder()
                .username(signupRequest.getUsername())
                .email(signupRequest.getEmail())
                .password(passwordEncoder.encode(signupRequest.getPassword())) // Encodage sécurisé du mot de passe
                .firstName(signupRequest.getFirstName())
                .lastName(signupRequest.getLastName())
                .build();

        userRepository.save(user); // Sauvegarde de l'utilisateur en base de données
        return "Utilisateur enregistré avec succès !";
    }
}
