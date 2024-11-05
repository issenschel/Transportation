package com.example.transportation.repositories;

import com.example.transportation.entitys.TokenVersion;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenVersionRepository extends CrudRepository<TokenVersion, Integer> {
    Optional<TokenVersion> findByVersion(String token);
}
