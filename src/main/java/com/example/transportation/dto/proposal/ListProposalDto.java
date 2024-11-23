package com.example.transportation.dto.proposal;

import lombok.Data;

import java.util.List;

@Data
public class ListProposalDto {
    List<ProposalResponseDto> proposalsList;
    int count;
}
