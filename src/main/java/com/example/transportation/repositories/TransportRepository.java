package com.example.transportation.repositories;

import com.example.transportation.entitys.Transport;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TransportRepository extends CrudRepository<Transport, Byte> {
    Optional<Transport> findById(byte id);
}
