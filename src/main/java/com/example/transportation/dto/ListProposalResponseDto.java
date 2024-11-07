package com.example.transportation.dto;

import lombok.Data;

import java.util.List;

@Data
public class ListProposalResponseDto {
    List<ProposalResponseDto> proposals;
    int count;

}
