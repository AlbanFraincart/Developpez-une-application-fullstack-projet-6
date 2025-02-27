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
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository,
                       UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
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
            // Pas d'utilisateur authentifié
            throw new UserNotFoundException("Utilisateur non trouvé ou non authentifié");
        }

        String currentEmail = userDetails.getUsername();
        User user = userRepository.findByEmail(currentEmail)
                .orElseThrow(() -> new UserNotFoundException("Utilisateur non trouvé"));

        // Mise à jour du username s'il est fourni et non vide
        if (StringUtils.hasText(updateRequest.getUsername())) {
            user.setUsername(updateRequest.getUsername());
        }

        // Mise à jour de l'email s'il est fourni et différent
        if (StringUtils.hasText(updateRequest.getEmail())
                && !updateRequest.getEmail().equalsIgnoreCase(user.getEmail())) {

            // Vérifier que le nouvel email n'est pas déjà utilisé
            if (userRepository.existsByEmail(updateRequest.getEmail())) {
                throw new EmailAlreadyUsedException("Erreur : L'email est déjà utilisé !");
            }
            user.setEmail(updateRequest.getEmail());
        }

        userRepository.save(user);
        return userMapper.toDto(user);
    }
}
