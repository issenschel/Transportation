package com.example.transportation.controllers;

import com.example.transportation.dto.ProposalDto;
import com.example.transportation.entitys.Proposal;
import com.example.transportation.services.ProposalService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequiredArgsConstructor
public class ProposalController {
    private final ProposalService proposalService;

    @PostMapping("/createNewProposal")
    public ResponseEntity<?> createNewProposal(@Valid @RequestBody ProposalDto proposalDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        Proposal proposal = proposalService.createProposalForCurrentUser(proposalDto);
        if(proposal == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Пользователь не найден");
        }
        return ResponseEntity.ok().build();
    }
}
