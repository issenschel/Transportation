package com.example.transportation.repositories;

import com.example.transportation.dto.ProposalResponseDto;
import com.example.transportation.entitys.Proposal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface ProposalRepository extends JpaRepository<Proposal, Integer> {
    @Query("SELECT new com.example.transportation.dto.ProposalResponseDto(t.id, t.client.id, t.senderAddress, t.recipientAddress, t.dateDispatch, t.dateReceipt," +
            " t.transport.name, t.budget, t.description, t.status) FROM Proposal t WHERE t.client.id = :clientId")
    Page<ProposalResponseDto> findByClientId (int clientId, Pageable pageable);

    @Query("SELECT new com.example.transportation.dto.ProposalResponseDto(t.id, t.client.id,t.senderAddress,t.recipientAddress,t.dateDispatch,t.dateReceipt," +
            "t.transport.name,t.budget,t.description,t.status) FROM Proposal t")
    Page<ProposalResponseDto> findAllProposalResponseDto(Pageable pageable);
}
