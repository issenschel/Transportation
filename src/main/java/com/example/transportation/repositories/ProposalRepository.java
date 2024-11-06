package com.example.transportation.repositories;

import com.example.transportation.entitys.Proposal;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProposalRepository extends CrudRepository<Proposal, Integer> {
}
