package com.example.transportation.interfaces;

import com.example.transportation.dto.RegistrationUserDto;

import java.util.Optional;

@FunctionalInterface
public interface ValidationRule {
    Optional<String> validate(RegistrationUserDto registrationUserDto);
}
