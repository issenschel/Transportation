package com.example.transportation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class AuthTokenDto {
    private String token;
    private List<String> role;
}
