package com.openclassrooms.mddapi.payload.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateUserRequest {
    @Size(max = 20)
    private String username;

    @Email
    @Size(max = 50)
    private String email;

    @Size(min = 5, max = 120, message = "Le mot de passe doit contenir entre 8 et 120 caractères")
    private String password;
}
