package com.example.transportation.dto;

import com.example.transportation.enums.ProposalStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProposalStatusDto {
    @NotNull(message = "Не указано поле status")
    private ProposalStatus status;
}
