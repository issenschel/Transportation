package com.example.transportation.services;

import com.example.transportation.dto.ProposalDto;
import com.example.transportation.entitys.Client;
import com.example.transportation.entitys.Proposal;
import com.example.transportation.entitys.Transport;
import com.example.transportation.entitys.User;
import com.example.transportation.enums.ProposalStatus;
import com.example.transportation.repositories.ProposalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProposalService {
    private final ProposalRepository proposalRepository;
    private final UserService userService;
    private final TransportService transportService;

    @Transactional
    public Proposal createProposalForCurrentUser(ProposalDto proposalDto) {
        Optional<User> user = userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        return user.map(
                us -> {
                    Client client = us.getClient();
                    Optional<Transport> transport = transportService.findById(proposalDto.getTransport());
                    return createNewProposal(proposalDto,client,transport.get());
                }
        ).orElse(null);
    }


    public Proposal createNewProposal(ProposalDto proposalDto, Client client, Transport transport) {
        Proposal proposal = new Proposal();
        proposal.setClient(client);
        proposal.setDateDispatch(proposalDto.getDateDispatch());
        proposal.setDateReceipt(proposalDto.getDateReceipt());
        proposal.setDescription(proposalDto.getDescription());
        proposal.setBudget(proposalDto.getBudget());
        proposal.setSenderAddress(proposalDto.getSenderAddress());
        proposal.setRecipientAddress(proposalDto.getRecipientAddress());
        proposal.setTransport(transport);
        proposal.setStatus(ProposalStatus.PENDING);
        proposalRepository.save(proposal);
        return proposal;
    }
}
