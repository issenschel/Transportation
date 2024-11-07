package com.example.transportation.services;

import com.example.transportation.dto.TransportDto;
import com.example.transportation.entitys.Transport;
import com.example.transportation.repositories.TransportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TransportService {
    private final TransportRepository transportRepository;

    public Optional<Transport> findById(byte id) {
        return transportRepository.findById(id);
    }

    public List<TransportDto> findAllTransportDto() {
        return transportRepository.findAllTransportDto();
    }
}
