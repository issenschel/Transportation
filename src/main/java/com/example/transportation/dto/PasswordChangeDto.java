package com.example.transportation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PasswordChangeDto {
    @NotBlank(message = "Старый пароль не может быть пустым")
    @Size(min = 8, max = 30, message = "Старый пароль не может быть меньше 8 или больше 30")
    private String oldPassword;

    @NotBlank(message = "Пароль не может быть пустым")
    @Size(min = 8, max = 30, message = "Пароль не может быть меньше 8 или больше 30")
    private String password;

    @NotBlank(message = "Подтверждение пароля не может быть пустым")
    @Size(min = 8, max = 30, message = "Подтверждение пароля не может быть меньше 8 или больше 30")
    private String confirmPassword;
}