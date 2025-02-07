package com.openclassrooms.mddapi.security;

import com.openclassrooms.mddapi.entities.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

/**
 * Cette classe adapte l'entité User pour l'utiliser avec Spring Security.
 */
public class UserDetailsImpl implements UserDetails {

    private final Long id;
    private final String username;
    private final String email;
    private final String password;

    // Même si on n'a pas de rôles dans User, on fournit une autorité par défaut.
    private final Collection<? extends GrantedAuthority> authorities;

    public UserDetailsImpl(Long id, String username, String email, String password,
                           Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }

    /**
     * Méthode de fabrique pour construire un UserDetailsImpl à partir d'une entité User.
     */
    public static UserDetailsImpl build(User user) {
        // On attribue une autorité par défaut "ROLE_USER".
        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_USER");
        return new UserDetailsImpl(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                Collections.singletonList(authority)
        );
    }

    // Getter pour l'ID (facultatif, utile pour des opérations ultérieures)
    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    // Ces méthodes renvoient true pour simplifier ; tu peux ajouter de la logique si nécessaire.
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
