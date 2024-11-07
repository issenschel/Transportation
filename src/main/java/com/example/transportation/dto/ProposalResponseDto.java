package com.example.transportation.dto;

import com.example.transportation.enums.ProposalStatus;
import lombok.Data;

import java.time.LocalDate;

@Data
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