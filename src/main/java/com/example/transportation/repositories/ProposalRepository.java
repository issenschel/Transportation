package com.example.transportation.repositories;

import com.example.transportation.entitys.Proposal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProposalRepository extends JpaRepository<Proposal, Integer> {
    Page<Proposal> findByClientId (int clientId, Pageable pageable);
}
