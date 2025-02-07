package com.openclassrooms.mddapi.payload;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JwtResponse {
    private String token;
    private String type; // par convention "Bearer"
    private String username;
    private String email;

    // Si tu souhaites fixer la valeur du type, tu peux ajouter un second constructeur ou initialiser dans le constructeur principal
    public JwtResponse(String token, String username, String email) {
        this.token = token;
        this.type = "Bearer";
        this.username = username;
        this.email = email;
    }
}
