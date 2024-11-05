package com.example.transportation.repositories;

import com.example.transportation.entitys.Client;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends CrudRepository<Client, Integer> {
    Optional<Client> findByEmail(String email);
    Optional<Client> findByPhone(String phone);
}
