package com.example.transportation.dto.other;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class StatusResponseDto {
    private String message;
    private HttpStatus status;
}
