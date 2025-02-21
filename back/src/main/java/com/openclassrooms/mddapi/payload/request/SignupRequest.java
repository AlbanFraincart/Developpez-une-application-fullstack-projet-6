package com.openclassrooms.mddapi.payload.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SignupRequest {
    @NotBlank
    @Size(max = 20)
    private String username;

    @NotBlank
    @Email
    @Size(max = 50)
    private String email;

    @NotBlank
    @Size(min = 6, max = 120)
    private String password;

//    @NotBlank
//    @Size(max = 20)
//    private String firstName;
//
//    @NotBlank
//    @Size(max = 20)
//    private String lastName;
}
