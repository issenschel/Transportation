package com.example.transportation.dto.transport;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TransportDto {
    private Byte id;
    private String name;
    private String photo;
    private String description;
}
