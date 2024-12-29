package project.bioinformatics.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
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
    public BioUserLoginResponseDto register(@Valid @RequestBody BioUserRegisterRequestDto request) {
        BioUserLoginRequestDto loginRequest = bioUserService.register(request);
        return authenticationService.authenticate(loginRequest);
    }

    @PostMapping("/login")
    public BioUserLoginResponseDto login(@Valid @RequestBody BioUserLoginRequestDto request) {
        return authenticationService.authenticate(request);
    }

    @GetMapping
    public BioUserResponseDto userInfo() {
        return bioUserService.getUserInfo();
    }
}
