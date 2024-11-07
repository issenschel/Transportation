package com.example.transportation.controllers;

import com.example.transportation.dto.ListProposalDto;
import com.example.transportation.dto.ProposalRequestDto;
import com.example.transportation.dto.TransportDto;
import com.example.transportation.entitys.Proposal;
import com.example.transportation.services.ProposalService;
import com.example.transportation.services.TransportService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
public class ProposalController {
    private final ProposalService proposalService;
    private final TransportService transportService;

    @PostMapping("/createNewProposal")
    public ResponseEntity<?> createNewProposal(@Valid @RequestBody ProposalRequestDto proposalRequestDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        Proposal proposal = proposalService.createProposalForCurrentUser(proposalRequestDto);
        if(proposal == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Пользователь не найден");
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/createNewProposal")
    public ResponseEntity<?> createNewProposal() {
        List<TransportDto> transportDto = transportService.findAllTransportDto();
        return ResponseEntity.ok().body(transportDto);
    }

    @GetMapping("/getProposals")
    public ResponseEntity<?> getProposals(@RequestParam(name = "page") int page) {
        ListProposalDto listProposalDto = proposalService.getProposals(page);
        return ResponseEntity.ok().body(listProposalDto);
    }
}
