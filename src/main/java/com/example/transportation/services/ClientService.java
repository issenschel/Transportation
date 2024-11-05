package com.example.transportation.services;

import com.example.transportation.dto.RegistrationUserDto;
import com.example.transportation.entitys.Client;
import com.example.transportation.entitys.User;
import com.example.transportation.repositories.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClientService {
    private final ClientRepository clientRepository;

    public Client createNewClient(RegistrationUserDto registrationUserDto, User user){
        Client client = new Client();
        client.setEmail(registrationUserDto.getEmail());
        client.setName(registrationUserDto.getName());
        client.setSurname(registrationUserDto.getSurname());
        client.setPhone(registrationUserDto.getPhone());
        client.setBirthdate(registrationUserDto.getBirthday());
        client.setUser(user);
        if (!registrationUserDto.getPatronymic().isBlank()) {
            client.setPatronymic(registrationUserDto.getPatronymic());
        }
        return clientRepository.save(client);
    }

    public Optional<Client> findByEmail(String email) {
        return clientRepository.findByEmail(email);
    }

    public Optional<Client> findByPhone(String phone) {
        return clientRepository.findByPhone(phone);
    }
}
