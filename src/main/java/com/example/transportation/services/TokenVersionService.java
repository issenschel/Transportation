package com.example.transportation.services;

import com.example.transportation.entitys.TokenVersion;
import com.example.transportation.repositories.TokenVersionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TokenVersionService {
    private final TokenVersionRepository tokenVersionRepository;

    public Optional<TokenVersion> findByVersion(String token) {
        return tokenVersionRepository.findByVersion(token);
    }
}
