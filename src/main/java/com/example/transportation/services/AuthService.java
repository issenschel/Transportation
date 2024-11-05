package com.example.transportation.services;

import com.example.transportation.dto.JwtRequestDto;
import com.example.transportation.entitys.User;
import com.example.transportation.utils.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtTokenUtils jwtTokenUtils;

    public String createAuthToken(@RequestBody JwtRequestDto authRequest) throws BadCredentialsException {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        UserDetails userDetails = userService.loadUserByUsername(authRequest.getUsername());
        Optional<User> user = userService.findByUsername(authRequest.getUsername());
        String versionId = user.get().getTokenVersion().getVersion();
        return jwtTokenUtils.generateToken(userDetails, versionId);
    }
}
