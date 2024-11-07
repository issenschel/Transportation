package com.example.transportation.repositories;

import com.example.transportation.dto.TransportDto;
import com.example.transportation.entitys.Transport;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransportRepository extends CrudRepository<Transport, Byte> {
    Optional<Transport> findById(byte id);

    @Query("SELECT new com.example.transportation.dto.TransportDto(t.id, t.name) FROM Transport t")
    List<TransportDto> findAllTransportDto();
}
