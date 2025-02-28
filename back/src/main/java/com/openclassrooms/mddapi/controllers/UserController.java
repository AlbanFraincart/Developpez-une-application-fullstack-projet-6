package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.dto.UserDto;
import com.openclassrooms.mddapi.payload.request.UpdateUserRequest;
import com.openclassrooms.mddapi.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Endpoint sécurisé pour récupérer les informations de l'utilisateur connecté.
     */
    @GetMapping("/me")
    public ResponseEntity<UserDto> getCurrentUser() {
        Optional<UserDto> user = userService.getCurrentUser();
        return user.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(401).build());
    }

    /**
     * Endpoint pour mettre à jour l'username et/ou l'email de l'utilisateur connecté.
     * Si l'utilisateur n'est pas connecté, on aura un 404 ou un 401 plus haut dans la stack.
     */
    @PatchMapping("/me")
    public ResponseEntity<UserDto> updateCurrentUser(@Valid @RequestBody UpdateUserRequest updateUserRequest) {
        UserDto updatedUser = userService.updateCurrentUser(updateUserRequest);
        return ResponseEntity.ok(updatedUser);
    }
}
