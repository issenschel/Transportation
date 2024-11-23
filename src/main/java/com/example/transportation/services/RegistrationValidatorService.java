package com.example.transportation.services;

import com.example.transportation.dto.auth.EmailDto;
import com.example.transportation.dto.auth.RegistrationUserDto;
import com.example.transportation.interfaces.ValidationRule;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RegistrationValidatorService {
    private final UserService userService;
    private final ClientService clientService;
    private final EmailService emailService;

    private final Map<String, ValidationRule> validationRules = new HashMap<>();

    @PostConstruct
    public void init() {
        validationRules.put("username", this::validateUsername);
        validationRules.put("phone", this::validatePhone);
        validationRules.put("passwordMatch", this::validatePasswordMatch);
        validationRules.put("code", this::validateCode);
    }

    public Map<String, String> validate(RegistrationUserDto registrationUserDto) {
        Map<String, String> errors = new HashMap<>();

        validationRules.forEach((field, rule) ->
                rule.validate(registrationUserDto).ifPresent(errorMessage -> errors.put(field, errorMessage)));

        return errors;
    }

    public Optional<String> validateUsername(RegistrationUserDto registrationUserDto) {
        return userService.findByUsername(registrationUserDto.getUsername())
                .map(user -> "Логин уже занят");
    }

    public Optional<String> validatePhone(RegistrationUserDto registrationUserDto) {
        return clientService.findByPhone(registrationUserDto.getPhone())
                .map(user -> "Телефон занят");
    }

    public Optional<String> validatePasswordMatch(RegistrationUserDto registrationUserDto) {
        if (!registrationUserDto.getPassword().equals(registrationUserDto.getConfirmPassword())) {
            return Optional.of("Пароли не совпадают");
        }
        return Optional.empty();
    }

    public Optional<String> validateCode(RegistrationUserDto registrationUserDto){
        if(!emailService.validateCode(registrationUserDto.getEmail(), registrationUserDto.getCode())){
            return  Optional.of("Неверный код");
        }
        return Optional.empty();
    }

    public Optional<String> validateEmail(EmailDto emailDto) {
        return clientService.findByEmail(emailDto.getEmail())
                .map(user -> "Почта уже занята");
    }
}

