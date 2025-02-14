package com.openclassrooms.mddapi.security;

import com.openclassrooms.mddapi.entities.User;
import com.openclassrooms.mddapi.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Service de récupération des utilisateurs pour Spring Security.
 * <p>
 * Il permet de charger un utilisateur à partir de son email et d'obtenir ses informations d'authentification.
 * </p>
 */
@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    /**
     * Charge un utilisateur en fonction de son email.
     *
     * @param email L'email de l'utilisateur à charger.
     * @return Un objet {@link UserDetails} représentant l'utilisateur.
     * @throws UsernameNotFoundException si l'utilisateur n'existe pas.
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Recherche dans le repository par email
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé avec l'email : " + email));
        // Construit l'objet UserDetails à partir de l'entité User
        return UserDetailsImpl.build(user);
    }
}
