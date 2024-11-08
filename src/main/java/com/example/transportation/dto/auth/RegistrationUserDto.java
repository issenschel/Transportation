package com.example.transportation.dto.auth;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class RegistrationUserDto {
    @NotBlank(message = "Не указано поле username")
    @Size(min = 3, max = 30, message = "Логин не может быть меньше 3 или больше 30")
    private String username;
    @NotBlank(message = "Не указано поле password")
    @Size(min = 8, max = 30, message = "Пароль не может быть меньше 8 или больше 30")
    private String password;
    @NotBlank(message = "Не указано поле confirmPassword")
    @Size(min = 8, max = 30, message = "Подтверждение пароля не может быть меньше 8 или больше 30")
    private String confirmPassword;
    @NotBlank(message = "Не указано поле phone")
    @Pattern(regexp = "^[0-9]{7,12}$", message = "Неверный формат телефонного номера")
    private String phone;
    @NotBlank(message = "Не указано поле email")
    @Email(message = "Почта должна быть валидной")
    private String email;
    @NotBlank(message = "Не указано поле name")
    @Size(min = 1, max = 50, message = "Имя не может быть меньше 1 или больше 50")
    private String name;
    @NotNull(message = "Не указано поле birthday")
    @Past(message = "Неправильно указана дата")
    private LocalDate birthday;
    @NotBlank(message = "Не указано поле surname")
    @Size(min = 1, max = 50, message = "Фамилия не может быть меньше 1 или больше 50")
    private String surname;
    @NotNull(message = "Не указано поле patronymic")
    @Size(max = 50, message = "Отчество не может быть больше 50")
    private String patronymic;
    @NotNull(message = "Не указано поле code")
    @Size(max = 6, message = "Код не может быть больше 6")
    private String code;
}
