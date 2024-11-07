package com.example.transportation.services;

import com.example.transportation.dto.AuthTokenDto;
import com.example.transportation.dto.ClientDto;
import com.example.transportation.dto.RegistrationUserDto;
import com.example.transportation.dto.JwtRequestDto;
import com.example.transportation.entitys.Client;
import com.example.transportation.entitys.User;
import com.example.transportation.utils.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final ClientService clientService;
    private final JwtTokenUtils jwtTokenUtils;
    private final TokenVersionService tokenVersionService;
    private final AssumptionService assumptionService;

    public AuthTokenDto createAuthToken(@RequestBody JwtRequestDto authRequest) throws BadCredentialsException {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        UserDetails userDetails = userService.loadUserByUsername(authRequest.getUsername());
        Optional<User> user = userService.findByUsername(authRequest.getUsername());
        String versionId = user.get().getTokenVersion().getVersion();
        List<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
        String token = jwtTokenUtils.generateToken(userDetails, versionId);
        return new AuthTokenDto(token,roles);
    }

    @Transactional
    public ClientDto createNewUser(@RequestBody RegistrationUserDto registrationUserDto) {
        User user = userService.createNewUser(registrationUserDto.getUsername(), registrationUserDto.getPassword());
        Client client = clientService.createNewClient(registrationUserDto, user);
        String token = UUID.randomUUID().toString();
        tokenVersionService.createNewTokenVersion(user,token);
        assumptionService.findByEmail(registrationUserDto.getEmail()).ifPresent(assumptionService::delete);
        return new ClientDto(client.getId(), client.getUser().getUsername(),
                client.getEmail(), client.getPhone(),client.getName(), client.getSurname(), client.getPatronymic());
    }
}
