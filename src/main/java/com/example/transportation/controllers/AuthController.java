package com.example.transportation.controllers;

import com.example.transportation.dto.EmailDto;
import com.example.transportation.dto.JwtRequestDto;
import com.example.transportation.dto.JwtResponseDto;
import com.example.transportation.dto.RegistrationUserDto;
import com.example.transportation.services.AuthService;
import com.example.transportation.services.EmailService;
import com.example.transportation.services.RegistrationValidatorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final EmailService emailService;
    private final RegistrationValidatorService registrationValidatorService;


    @PostMapping("/auth")
    public ResponseEntity<?> createAuthToken(@Valid @RequestBody JwtRequestDto authRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        try {
            return ResponseEntity.ok(new JwtResponseDto(authService.createAuthToken(authRequest)));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Неверный логин или пароль");
        }
    }

    @PostMapping("/registration")
    public ResponseEntity<?> createNewUser(@Valid @RequestBody RegistrationUserDto registrationUserDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        Map<String, String> validationsErrors = registrationValidatorService.validate(registrationUserDto);
        if (!validationsErrors.isEmpty()){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(validationsErrors);
        }
        return ResponseEntity.ok(authService.createNewUser(registrationUserDto));
    }

    @PostMapping("/sendCode")
    public ResponseEntity<?> sendCode(@Valid @RequestBody EmailDto emailDto, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        Optional<String> validate = registrationValidatorService.validateEmail(emailDto);
        if (validate.isPresent()){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(validate);
        }
        return ResponseEntity.ok().body(emailService.sendCode(emailDto.getEmail()));
    }
}
