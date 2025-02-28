package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.dto.UserDto;
import com.openclassrooms.mddapi.entities.User;
import com.openclassrooms.mddapi.exception.EmailAlreadyUsedException;
import com.openclassrooms.mddapi.exception.UserNotFoundException;
import com.openclassrooms.mddapi.mapper.UserMapper;
import com.openclassrooms.mddapi.payload.request.UpdateUserRequest;
import com.openclassrooms.mddapi.repositories.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
                       UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Récupère les informations de l'utilisateur connecté via le SecurityContext
     * et renvoie un Optional pour le cas où il ne serait pas authentifié.
     */
    public Optional<UserDto> getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails userDetails) {
            String email = userDetails.getUsername(); // Email utilisé comme identifiant
            return userRepository.findByEmail(email)
                    .map(userMapper::toDto);
        }
        return Optional.empty();
    }

    /**
     * Met à jour l'username et/ou l'email de l'utilisateur connecté.
     * Renvoie un UserDto ou lève une exception si utilisateur introuvable ou email déjà pris.
     */
    public UserDto updateCurrentUser(UpdateUserRequest updateRequest) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!(principal instanceof UserDetails userDetails)) {
            throw new UserNotFoundException("Utilisateur non trouvé ou non authentifié");
        }

        String currentEmail = userDetails.getUsername();
        User user = userRepository.findByEmail(currentEmail)
                .orElseThrow(() -> new UserNotFoundException("Utilisateur non trouvé"));

        if (StringUtils.hasText(updateRequest.getUsername())) {
            user.setUsername(updateRequest.getUsername());
        }

        if (StringUtils.hasText(updateRequest.getEmail())
                && !updateRequest.getEmail().equalsIgnoreCase(user.getEmail())) {
            if (userRepository.existsByEmail(updateRequest.getEmail())) {
                throw new EmailAlreadyUsedException("Erreur : L'email est déjà utilisé !");
            }
            user.setEmail(updateRequest.getEmail());
        }

        // ✅ Vérification et encodage du mot de passe
        if (StringUtils.hasText(updateRequest.getPassword())) {
            user.setPassword(passwordEncoder.encode(updateRequest.getPassword()));
        }

        userRepository.save(user);
        return userMapper.toDto(user);
    }
}
