package com.example.transportation.services;

import com.example.transportation.dto.other.StatusResponseDto;
import com.example.transportation.dto.profile.EmailChangeDto;
import com.example.transportation.dto.profile.LoginChangeDto;
import com.example.transportation.dto.profile.PasswordChangeDto;
import com.example.transportation.dto.profile.ProfileDto;
import com.example.transportation.dto.proposal.ListProposalDto;
import com.example.transportation.dto.proposal.ProposalResponseDto;
import com.example.transportation.entitys.User;
import com.example.transportation.utils.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProfileService {
    private final UserService userService;
    private final ClientService clientService;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtils jwtTokenUtils;
    private final EmailService emailService;
    private final AssumptionService assumptionService;
    private final ProposalService proposalService;

    @Value("${upload.path}")
    private String uploadPath;

    @Transactional
    public StatusResponseDto changeLogin(LoginChangeDto loginChangeDto) {
        Optional<User> user = userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        return user.map(us -> {
            boolean isUsernameAvailable = userService.findByUsername(loginChangeDto.getNewUsername()).isEmpty();
            boolean isPasswordValid = passwordEncoder.matches(loginChangeDto.getPassword(), us.getPassword());
            if (isUsernameAvailable && isPasswordValid) {
                us.setUsername(loginChangeDto.getNewUsername());
                us.getTokenVersion().setVersion(UUID.randomUUID().toString());
                userService.saveUser(us);
                UserDetails userDetails = userService.loadUserByUsername(us.getUsername());
                String versionId = us.getTokenVersion().getVersion();
                String token = jwtTokenUtils.generateToken(userDetails, versionId);
                return new StatusResponseDto(token, HttpStatus.OK);
            } else {
                return new StatusResponseDto(isUsernameAvailable ? "Неверный пароль" : "Логин уже занят", HttpStatus.CONFLICT);
            }
        }).orElseGet(() -> new StatusResponseDto("Пользователь не найден", HttpStatus.NOT_FOUND));
    }

    @Transactional
    public StatusResponseDto changePassword(PasswordChangeDto passwordChangeDto) {
        Optional<User> user = userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        return user.map(us -> {
            boolean isPasswordEqual = passwordChangeDto.getPassword().equals(passwordChangeDto.getConfirmPassword());
            boolean isPasswordValid = passwordEncoder.matches(passwordChangeDto.getOldPassword(), us.getPassword());
            if (isPasswordEqual && isPasswordValid) {
                us.setPassword(passwordEncoder.encode(passwordChangeDto.getPassword()));
                userService.saveUser(us);
                UserDetails userDetails = userService.loadUserByUsername(us.getUsername());
                String versionId = us.getTokenVersion().getVersion();
                String token = jwtTokenUtils.generateToken(userDetails, versionId);
                return new StatusResponseDto(token, HttpStatus.OK);
            } else {
                return new StatusResponseDto(isPasswordValid ? "Пароли не совпадают" : "Неверный пароль", HttpStatus.CONFLICT);
            }
        }).orElseGet(() -> new StatusResponseDto("Пользователь не найден", HttpStatus.NOT_FOUND));
    }

    @Transactional
    public StatusResponseDto changeEmail(EmailChangeDto emailChangeDto) {
        Optional<User> user = userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        return user.map(us -> {
            boolean emailAvailable = clientService.findByEmail(emailChangeDto.getEmail()).isEmpty();
            boolean validateCode = emailService.validateCode(user.get().getClient().getEmail(), emailChangeDto.getCode());
            if (emailAvailable && validateCode) {
                assumptionService.findByEmail(us.getClient().getEmail()).ifPresent(assumptionService::delete);
                us.getClient().setEmail(emailChangeDto.getEmail());
                us.getTokenVersion().setVersion(UUID.randomUUID().toString());
                clientService.save(us.getClient());
                UserDetails userDetails = userService.loadUserByUsername(us.getUsername());
                String versionId = us.getTokenVersion().getVersion();
                String token = jwtTokenUtils.generateToken(userDetails, versionId);
                return new StatusResponseDto(token, HttpStatus.OK);
            } else {
                return new StatusResponseDto(emailAvailable ? "Неверный код" : "Новая почта уже занята", HttpStatus.CONFLICT);
            }
        }).orElseGet(() -> new StatusResponseDto("Пользователь не найден", HttpStatus.NOT_FOUND));
    }

    @Transactional
    public StatusResponseDto changePhoto(MultipartFile photo) {
        if (photo != null && !photo.getContentType().matches("image/.*")) {
            Optional<User> user = userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
            String uuidFile = UUID.randomUUID().toString();
            String result = uuidFile + "." + photo.getOriginalFilename();
            return user.map(us -> {
                try {
                    if (us.getClient().getPhoto() != null) {
                        Files.delete(Path.of(uploadPath + "/" + us.getClient().getPhoto()));
                    }
                    photo.transferTo(new File(uploadPath + "/" + result));
                } catch (IOException e) {
                    return new StatusResponseDto("Что-то пошло не так", HttpStatus.CONFLICT);
                }
                us.getClient().setPhoto(result);
                clientService.save(us.getClient());
                return new StatusResponseDto("Успешное изменение фотографии", HttpStatus.OK);
            }).orElseGet(() -> new StatusResponseDto("Пользователь не найден", HttpStatus.NOT_FOUND));
        }
        return new StatusResponseDto("Неверный формат фотографии", HttpStatus.BAD_REQUEST);
    }

    public ProfileDto getData() {
        Optional<User> user = userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        return user.map(
                us -> new ProfileDto(us.getId(), us.getUsername(), us.getClient().getEmail(), us.getClient().getPhone())
        ).orElse(null);
    }

    public ListProposalDto getProposal(int page){
        Optional<User> user = userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        return user.map(us ->{
            ListProposalDto listProposalResponseDto = new ListProposalDto();
            Pageable pageable = PageRequest.of(page, 6);
            Page<ProposalResponseDto> proposalPage = proposalService.getProposalsByClientId(us.getClient().getId(), pageable);
            listProposalResponseDto.setProposalsList(proposalPage.getContent());
            listProposalResponseDto.setCount(proposalPage.getTotalPages());
            return listProposalResponseDto;
        }).orElse(null);
    }

    public String getPhoto() {
        return userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).map(user -> user.getClient().getPhoto()
        ).orElse(null);
    }
}