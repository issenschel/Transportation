package com.example.transportation.controllers;

import com.example.transportation.services.TransportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transport")
@RequiredArgsConstructor
public class TransportController {
    private final TransportService transportService;

    @GetMapping()
    public ResponseEntity<?> getTransport() {
        return ResponseEntity.ok().body(transportService.findAllTransport());
    }
}
