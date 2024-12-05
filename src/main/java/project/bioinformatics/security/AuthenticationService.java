package project.bioinformatics.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import project.bioinformatics.dto.BioUserLoginRequestDto;
import project.bioinformatics.dto.BioUserLoginResponseDto;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public BioUserLoginResponseDto authenticate(BioUserLoginRequestDto request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        String token = jwtUtil.generateToken(authentication.getName());
        return new BioUserLoginResponseDto(token);
    }
}
