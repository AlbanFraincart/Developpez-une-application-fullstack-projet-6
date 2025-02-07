package com.openclassrooms.mddapi.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NonNull;

import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

@Data
public class UserDto {
    private Long id;

    @NonNull
    private String username;

    @NonNull
    @Size(max = 50)
    @Email
    private String email;

    @NonNull
    @Size(max = 20)
    private String lastName;

    @NonNull
    @Size(max = 20)
    private String firstName;

    @JsonIgnore
    @Size(max = 120)
    private String password;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}
