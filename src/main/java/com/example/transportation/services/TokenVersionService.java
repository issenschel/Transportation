package com.example.transportation.services;

import com.example.transportation.entitys.TokenVersion;
import com.example.transportation.entitys.User;
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

    public void createNewTokenVersion(User user,String token){
        TokenVersion tokenVersion = new TokenVersion();
        tokenVersion.setUser(user);
        tokenVersion.setVersion(token);
        tokenVersionRepository.save(tokenVersion);
    }

    public void save(TokenVersion tokenVersion) {
        tokenVersionRepository.save(tokenVersion);
    }
}
