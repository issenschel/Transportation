package com.example.transportation.controllers;

import com.example.transportation.dto.ListProposalDto;
import com.example.transportation.dto.ProposalStatusDto;
import com.example.transportation.dto.StatusResponseDto;
import com.example.transportation.services.ProposalService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController()
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final ProposalService proposalService;

    @GetMapping("/proposal")
    public ResponseEntity<?> getProposals(@RequestParam(name = "page") int page) {
        ListProposalDto listProposalDto = proposalService.getProposals(page);
        return ResponseEntity.ok().body(listProposalDto);
    }

    @PutMapping("/proposal")
    public ResponseEntity<?> changeStatus(@RequestParam(name = "id") int id, @Valid @RequestBody ProposalStatusDto proposalStatusDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        StatusResponseDto statusResponseDto = proposalService.changeStatus(id,proposalStatusDto);
        return ResponseEntity.status(statusResponseDto.getStatus()).body(statusResponseDto.getMessage());
    }
}
