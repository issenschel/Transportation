package com.example.transportation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ClientDto {
    private Integer id;
    private String username;
    private String email;
    private String phone;
    private String name;
    private String surname;
    private String patronymic;
}
