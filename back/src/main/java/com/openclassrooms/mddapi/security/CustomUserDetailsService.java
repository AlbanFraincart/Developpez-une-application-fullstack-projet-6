package com.openclassrooms.mddapi.security;

import com.openclassrooms.mddapi.entities.User;
import com.openclassrooms.mddapi.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Service qui charge un utilisateur à partir de la base de données.
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    // Injection du repository via le constructeur
    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Charge l'utilisateur par son email.
     * Ici, on considère que l'email est utilisé comme identifiant pour l'authentification.
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
