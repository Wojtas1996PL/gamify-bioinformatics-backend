package project.bioinformatics.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.bioinformatics.dto.BioUserLoginRequestDto;
import project.bioinformatics.dto.BioUserLoginResponseDto;
import project.bioinformatics.dto.BioUserRegisterRequestDto;
import project.bioinformatics.dto.BioUserResponseDto;
import project.bioinformatics.security.AuthenticationService;
import project.bioinformatics.service.BioUserService;

@RestController
@RequiredArgsConstructor
@RequestMapping ("/auth")
public class AuthenticationController {
    private final BioUserService bioUserService;
    private final AuthenticationService authenticationService;

    @PostMapping("/registration")
    public BioUserResponseDto register(@Valid @RequestBody BioUserRegisterRequestDto request) {
        return bioUserService.register(request);
    }

    @PostMapping("/login")
    public BioUserLoginResponseDto login(@Valid @RequestBody BioUserLoginRequestDto request) {
        return authenticationService.authenticate(request);
    }
}
