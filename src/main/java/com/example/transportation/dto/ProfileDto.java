package com.example.transportation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProfileDto {
    private Integer id;
    private String username;
    private String email;
    private String phone;
}
