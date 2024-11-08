package com.example.transportation.repositories;

import com.example.transportation.dto.transport.TransportDto;
import com.example.transportation.dto.transport.TransportProposalDto;
import com.example.transportation.entitys.Transport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransportRepository extends JpaRepository<Transport, Byte> {
    Optional<Transport> findById(byte id);

    @Query("SELECT new com.example.transportation.dto.transport.TransportProposalDto(t.id, t.name) FROM Transport t")
    List<TransportProposalDto> findAllTransportProposalDto();

    @Query("SELECT new com.example.transportation.dto.transport.TransportDto(t.id, t.name, t.photo, t.description) FROM Transport t")
    List<TransportDto> findAllTransportDto();
}
