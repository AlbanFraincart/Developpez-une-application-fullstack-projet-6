package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.entities.User;
import com.openclassrooms.mddapi.payload.JwtResponse;

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

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;

    public AuthService(AuthenticationManager authenticationManager,
                       UserRepository userRepository,
                       JwtUtils jwtUtils,
                       PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.jwtUtils = jwtUtils;
        this.passwordEncoder = passwordEncoder;
    }

    public JwtResponse login(LoginRequest loginRequest) {
        // Authentifier l'utilisateur
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Générer le token JWT
        String jwt = jwtUtils.generateJwtToken(authentication);

        // Récupérer l'utilisateur depuis la base de données (ou depuis le principal)
        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("Erreur : Utilisateur non trouvé."));

        // Retourner la réponse contenant le token, le username et l'email
        return new JwtResponse(jwt, "Bearer", user.getUsername(), user.getEmail());
    }

    public String register(SignupRequest signupRequest) {
        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            throw new RuntimeException("Erreur : L'email est déjà utilisé !");
        }
        // Créer un nouvel utilisateur avec le mot de passe encodé (bcrypt)
        User user = User.builder()
                .username(signupRequest.getUsername())
                .email(signupRequest.getEmail())
                .password(passwordEncoder.encode(signupRequest.getPassword()))
                .firstName(signupRequest.getFirstName())
                .lastName(signupRequest.getLastName())
                .build();

        userRepository.save(user);
        return "Utilisateur enregistré avec succès !";
    }
}
