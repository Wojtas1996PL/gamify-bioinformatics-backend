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
import project.bioinformatics.dto.ForgotPasswordRequest;
import project.bioinformatics.dto.biouserupdatedto.BioUserNameUpdateDto;
import project.bioinformatics.dto.biouserupdatedto.BioUserPhotoUpdateDto;
import project.bioinformatics.dto.biouserupdatedto.BioUserScorePointsUpdateDto;
import project.bioinformatics.dto.biouserupdatedto.BioUserUpdateDto;
import project.bioinformatics.dto.biouserupdatedto.BioUserUsernameUpdateDto;
import project.bioinformatics.exception.EntityNotFoundException;
import project.bioinformatics.exception.RegistrationException;
import project.bioinformatics.exception.UsernameTakenException;
import project.bioinformatics.mapper.BioUserMapper;
import project.bioinformatics.model.BioUser;
import project.bioinformatics.model.Role;
import project.bioinformatics.repository.biouser.BioUserRepository;
import project.bioinformatics.repository.role.RoleRepository;
import project.bioinformatics.security.JwtUtil;

@Service
@RequiredArgsConstructor
public class BioUserServiceImpl implements BioUserService {
    private final BioUserRepository bioUserRepository;
    private final BioUserMapper bioUserMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final EmailService emailService;
    private final JwtUtil jwtUtil;

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

    @Override
    public BioUserResponseDto changeUsername(BioUserUsernameUpdateDto usernameUpdateDto) {
        if (bioUserRepository.findByUsername(usernameUpdateDto.getUsername()).isPresent()) {
            throw new UsernameTakenException(
                    "The username " + usernameUpdateDto.getUsername() + " is already taken.");
        }
        BioUser loggedInUser = getAuthenticatedUser();
        loggedInUser.setUsername(usernameUpdateDto.getUsername());
        return bioUserMapper.toBioUserResponseDto(bioUserRepository.save(loggedInUser));
    }

    @Override
    public BioUserResponseDto changeName(BioUserNameUpdateDto nameUpdateDto) {
        BioUser loggedInUser = getAuthenticatedUser();
        loggedInUser.setName(nameUpdateDto.getName());
        return bioUserMapper.toBioUserResponseDto(bioUserRepository.save(loggedInUser));
    }

    @Override
    public BioUserResponseDto changePhoto(BioUserPhotoUpdateDto photoUpdateDto) {
        BioUser loggedInUser = getAuthenticatedUser();
        loggedInUser.setPhoto(photoUpdateDto.getPhoto());
        return bioUserMapper.toBioUserResponseDto(bioUserRepository.save(loggedInUser));
    }

    @Override
    public BioUserResponseDto addScorePoints(BioUserScorePointsUpdateDto scorePointsUpdateDto) {
        BioUser loggedInUser = getAuthenticatedUser();
        int scorePoints = loggedInUser.getScorePoints();
        scorePoints += scorePointsUpdateDto.getScorePoints();
        loggedInUser.setScorePoints(scorePoints);
        return bioUserMapper.toBioUserResponseDto(bioUserRepository.save(loggedInUser));
    }

    @Override
    public BioUserResponseDto updateUser(BioUserUpdateDto bioUserUpdateDto) {
        BioUser loggedInUser = getAuthenticatedUser();
        boolean emailChanged = false;

        if (bioUserUpdateDto.getUsername() != null && !bioUserUpdateDto.getUsername().isEmpty()) {
            if (bioUserRepository.findByUsername(bioUserUpdateDto.getUsername()).isPresent()) {
                throw new UsernameTakenException(
                        "The username " + bioUserUpdateDto.getUsername() + " is already taken.");
            }
            loggedInUser.setUsername(bioUserUpdateDto.getUsername());
        }

        if (bioUserUpdateDto.getName() != null && !bioUserUpdateDto.getName().isEmpty()) {
            loggedInUser.setName(bioUserUpdateDto.getName());
        }

        if (bioUserUpdateDto.getPhoto() != null && !bioUserUpdateDto.getPhoto().isEmpty()) {
            loggedInUser.setPhoto(bioUserUpdateDto.getPhoto());
        }
        if (bioUserUpdateDto.getPassword() != null && !bioUserUpdateDto.getPassword().isEmpty()) {
            String encodedPassword = passwordEncoder.encode(bioUserUpdateDto.getPassword());
            loggedInUser.setPassword(encodedPassword);
        }

        if (bioUserUpdateDto.getEmail() != null && !bioUserUpdateDto.getEmail().isEmpty()) {
            if (bioUserRepository.findByEmail(bioUserUpdateDto.getEmail()).isPresent()) {
                throw new RegistrationException(
                        "The email " + bioUserUpdateDto.getEmail() + " is already in use.");
            }
            loggedInUser.setEmail(bioUserUpdateDto.getEmail());
            emailChanged = true;
            emailService.sendChangePasswordEmails(loggedInUser.getUsername(),
                    loggedInUser.getEmail(), bioUserUpdateDto.getEmail());

        }
        bioUserRepository.save(loggedInUser);

        String newToken = null;
        if (emailChanged) {
            newToken = jwtUtil.generateToken(loggedInUser.getEmail());
        }

        BioUserResponseDto bioUserResponseDto = bioUserMapper.toBioUserResponseDto(loggedInUser);
        if (newToken != null) {
            bioUserResponseDto.setToken(newToken);
        }
        return bioUserResponseDto;
    }

    @Override
    public void emailCheck(ForgotPasswordRequest forgotPasswordRequest) {
        bioUserRepository.findByEmail(forgotPasswordRequest.getEmail())
                .orElseThrow(() -> new EntityNotFoundException(
                        "There is no account registered in database with email: "
                        + forgotPasswordRequest.getEmail()));
    }

    private BioUser getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return bioUserRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
