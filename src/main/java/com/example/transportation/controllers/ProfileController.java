package com.example.transportation.controllers;

import com.example.transportation.dto.auth.JwtResponseDto;
import com.example.transportation.dto.other.StatusResponseDto;
import com.example.transportation.dto.profile.EmailChangeDto;
import com.example.transportation.dto.profile.LoginChangeDto;
import com.example.transportation.dto.profile.PasswordChangeDto;
import com.example.transportation.dto.profile.ProfileDto;
import com.example.transportation.dto.proposal.ListProposalDto;
import com.example.transportation.services.ProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

@RestController
@RequestMapping("/profile")
@RequiredArgsConstructor
public class ProfileController {
    private final ProfileService profileService;

    @PostMapping("/changeLogin")
    public ResponseEntity<?> changeLogin(@Valid @RequestBody LoginChangeDto loginChangeDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        StatusResponseDto responseDto = profileService.changeLogin(loginChangeDto);
        return ResponseEntity.status(responseDto.getStatus()).body(new JwtResponseDto(responseDto.getMessage()));
    }

    @PostMapping("/changePassword")
    public ResponseEntity<?> changeLogin(@Valid @RequestBody PasswordChangeDto passwordChangeDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        StatusResponseDto responseDto = profileService.changePassword(passwordChangeDto);
        return ResponseEntity.status(responseDto.getStatus()).body(new JwtResponseDto(responseDto.getMessage()));
    }

    @PostMapping("/changeEmail")
    public ResponseEntity<?> changeEmail(@Valid @RequestBody EmailChangeDto emailChangeDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        StatusResponseDto responseDto = profileService.changeEmail(emailChangeDto);
        return ResponseEntity.status(responseDto.getStatus()).body(new JwtResponseDto(responseDto.getMessage()));
    }

    @PostMapping("/changePhoto")
    public ResponseEntity<?> changePhoto(@RequestParam("file") MultipartFile photo, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        StatusResponseDto responseDto = profileService.changePhoto(photo);
        return ResponseEntity.status(responseDto.getStatus()).body(responseDto.getMessage());
    }

    @GetMapping("/settings")
    public ResponseEntity<?> getData(){
        ProfileDto profileDto = profileService.getData();
        if (profileDto == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Пользователь не найден");
        }
        return ResponseEntity.ok().body(profileDto);
    }

    @GetMapping("/proposal")
    public ResponseEntity<?> getProposal(@RequestParam(name = "page") int page){
        ListProposalDto listProposalDto = profileService.getProposal(page);
        if (listProposalDto == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Пользователь не найден");
        }
        return ResponseEntity.ok().body(listProposalDto);
    }


    @GetMapping("/photo")
    public ResponseEntity<?> getPhoto(){
        String path = profileService.getPhoto();
        if (path == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Пользователь не найден");
        }
        return ResponseEntity.ok().body(path);
    }

}
