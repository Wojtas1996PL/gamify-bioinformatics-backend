package project.bioinformatics.service;

import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import project.bioinformatics.dto.BioUserLoginRequestDto;
import project.bioinformatics.dto.BioUserRegisterRequestDto;
import project.bioinformatics.dto.BioUserResponseDto;
import project.bioinformatics.exception.RegistrationException;
import project.bioinformatics.mapper.BioUserMapper;
import project.bioinformatics.model.BioUser;
import project.bioinformatics.model.Role;
import project.bioinformatics.repository.biouser.BioUserRepository;
import project.bioinformatics.repository.role.RoleRepository;

@Service
@RequiredArgsConstructor
public class BioUserServiceImpl implements BioUserService {
    private final BioUserRepository bioUserRepository;
    private final BioUserMapper bioUserMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Override
    public BioUserLoginRequestDto register(BioUserRegisterRequestDto requestDto)
            throws RegistrationException {
        if (bioUserRepository.findByEmail(requestDto.getEmail()).isPresent()) {
            throw new RegistrationException("User with email " + requestDto.getEmail()
                    + " already exists");
        }
        if (bioUserRepository.findByUsername(requestDto.getUsername()).isPresent()) {
            throw new RegistrationException("User with username " + requestDto.getUsername()
                    + " already exists");
        }

        Set<Role> roles;
        if (requestDto.getRoles() == null || requestDto.getRoles().isEmpty()) {
            roles = Set.of(roleRepository.findByName(Role.RoleName.ROLE_USER)
                    .orElseThrow(() -> new RegistrationException(
                            "Default role ROLE_USER not found")));
        } else {
            roles = requestDto.getRoles().stream()
                    .map(roleName -> roleRepository.findByName(roleName)
                            .orElseThrow(() -> new RegistrationException(
                                    "Role " + roleName + " not found")))
                    .collect(Collectors.toSet());
        }

        BioUser bioUser = bioUserMapper.toBioUser(requestDto);
        bioUser.setRoles(roles);
        bioUser.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        try {
            bioUserRepository.save(bioUser);
            return bioUserMapper.toBioUserLoginRequestDto(requestDto);
        } catch (DataIntegrityViolationException e) {
            throw new RegistrationException("Could not register user. Please"
                    + " ensure the username and email are unique.");
        }
    }

    @Override
    public BioUserResponseDto getUserInfo() {
        BioUser loggedInUser = getAuthenticatedUser();
        return bioUserMapper.toBioUserResponseDto(loggedInUser);
    }

    private BioUser getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return bioUserRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
