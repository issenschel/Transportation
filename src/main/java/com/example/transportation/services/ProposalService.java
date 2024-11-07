package com.example.transportation.services;

import com.example.transportation.dto.*;
import com.example.transportation.entitys.Client;
import com.example.transportation.entitys.Proposal;
import com.example.transportation.entitys.Transport;
import com.example.transportation.entitys.User;
import com.example.transportation.enums.ProposalStatus;
import com.example.transportation.repositories.ProposalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
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
    public Proposal createProposalForCurrentUser(ProposalRequestDto proposalRequestDto) {
        Optional<User> user = userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        return user.map(
                us -> {
                    Client client = us.getClient();
                    Optional<Transport> transport = transportService.findById(proposalRequestDto.getTransport());
                    return createNewProposal(proposalRequestDto,client,transport.get());
                }
        ).orElse(null);
    }


    public Proposal createNewProposal(ProposalRequestDto proposalRequestDto, Client client, Transport transport) {
        Proposal proposal = new Proposal();
        proposal.setClient(client);
        proposal.setDateDispatch(proposalRequestDto.getDateDispatch());
        proposal.setDateReceipt(proposalRequestDto.getDateReceipt());
        proposal.setDescription(proposalRequestDto.getDescription());
        proposal.setBudget(proposalRequestDto.getBudget());
        proposal.setSenderAddress(proposalRequestDto.getSenderAddress());
        proposal.setRecipientAddress(proposalRequestDto.getRecipientAddress());
        proposal.setTransport(transport);
        proposal.setStatus(ProposalStatus.PENDING);
        proposalRepository.save(proposal);
        return proposal;
    }

    public ListProposalDto getProposals(int page) {
        ListProposalDto listProposalDto = new ListProposalDto();
        PageRequest pageRequest = PageRequest.of(page, 6);
        Page<ProposalResponseDto> ordersPage = proposalRepository.findAllProposalResponseDto(pageRequest);
        listProposalDto.setProposalList(ordersPage.getContent());
        listProposalDto.setCount(ordersPage.getTotalPages());
        return listProposalDto;
    }

    public StatusResponseDto changeStatus(int id, ProposalStatusDto proposalStatusDto){
            Optional<Proposal> proposal = proposalRepository.findById(id);
            if (proposal.isPresent()) {
                proposal.get().setStatus(proposalStatusDto.getStatus());
                proposalRepository.save(proposal.get());
                return new StatusResponseDto("Статус изменён", HttpStatus.OK);
            }
            return new StatusResponseDto("Заявка не найдена", HttpStatus.NOT_FOUND);
    }

    public Page<ProposalResponseDto> getProposalsByClientId(int clientId, Pageable pageable) {
        return proposalRepository.findByClientId(clientId, pageable);
    }
}
