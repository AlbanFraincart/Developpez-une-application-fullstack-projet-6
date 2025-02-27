package com.openclassrooms.mddapi.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NonNull;

import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

@Data
public class UserDto {
    private Long id;

    @NotBlank
    @Size(max = 20)
    private String username;

    @NotBlank
    @Email
    private String email;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}
