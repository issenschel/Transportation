package com.example.transportation.dto;

import lombok.Data;

import java.util.List;

@Data
public class ListProposalDto {
    List<ProposalResponseDto> proposalList;
    int count;
}
