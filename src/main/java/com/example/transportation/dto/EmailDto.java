package com.example.transportation.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class EmailDto {
    @NotBlank(message = "Почта не может быть пустой")
    @Email(message = "Почта должна быть валидной")
    private String email;
}
