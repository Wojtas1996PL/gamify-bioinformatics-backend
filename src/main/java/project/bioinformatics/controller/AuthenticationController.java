package project.bioinformatics.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.bioinformatics.dto.BioUserLoginRequestDto;
import project.bioinformatics.dto.BioUserLoginResponseDto;
import project.bioinformatics.dto.BioUserRegisterRequestDto;
import project.bioinformatics.dto.ForgotPasswordRequest;
import project.bioinformatics.security.AuthenticationService;
import project.bioinformatics.service.BioUserService;
import project.bioinformatics.service.EmailService;

@RestController
@RequiredArgsConstructor
@RequestMapping ("/auth")
public class AuthenticationController {
    private final BioUserService bioUserService;
    private final AuthenticationService authenticationService;
    private final EmailService emailService;

    @PostMapping("/registration")
    public BioUserLoginResponseDto register(@Valid @RequestBody BioUserRegisterRequestDto request) {
        BioUserLoginRequestDto loginRequest = bioUserService.register(request);
        BioUserLoginResponseDto authenticated = authenticationService.authenticate(loginRequest);
        emailService.sendWelcomeEmail(request);
        return authenticated;
    }

    @PostMapping("/login")
    public BioUserLoginResponseDto login(@Valid @RequestBody BioUserLoginRequestDto request) {
        return authenticationService.authenticate(request);
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<Void> forgotPassword(
            @Valid @RequestBody ForgotPasswordRequest forgotPasswordRequest) {
        bioUserService.emailCheck(forgotPasswordRequest);
        String token = authenticationService
                .generateResetPasswordToken(forgotPasswordRequest.getEmail());
        String resetLink = "http://localhost:5173/reset/set-password/" + token;
        emailService.sendResetPasswordLink(forgotPasswordRequest.getEmail(), resetLink);
        return ResponseEntity.ok().build();
    }
}
