package com.openclassrooms.mddapi.payload.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JwtResponse {
    private String token;
    private String username;
    private String email;
    @JsonIgnore
    private String password;


    // Si tu souhaites fixer la valeur du type, tu peux ajouter un second constructeur ou initialiser dans le constructeur principal
    public JwtResponse(String token, String username, String email) {
        this.token = token;
        this.username = username;
        this.email = email;
    }
}
