package com.example.transportation.dto.proposal;

import com.example.transportation.enums.ProposalStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class ProposalResponseDto {
    private int id;
    private int clientId;
    private String senderAddress;
    private String recipientAddress;
    private LocalDate dateDispatch;
    private LocalDate dateReceipt;
    private String transport;
    private Integer budget;
    private String description;
    private ProposalStatus status;
}