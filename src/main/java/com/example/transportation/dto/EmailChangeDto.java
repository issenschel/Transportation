package com.example.transportation.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class EmailChangeDto {
    @NotNull
    @Size(max = 6, message = "Код не может быть больше 6")
    private String code;
    @NotBlank(message = "Почта не может быть пустой")
    @Email(message = "Почта должна быть валидной")
    private String email;
}
