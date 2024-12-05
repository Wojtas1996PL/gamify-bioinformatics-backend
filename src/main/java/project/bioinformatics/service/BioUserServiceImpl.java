package project.bioinformatics.service;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import project.bioinformatics.dto.BioUserRegisterRequestDto;
import project.bioinformatics.dto.BioUserResponseDto;
import project.bioinformatics.exception.RegistrationException;
import project.bioinformatics.mapper.BioUserMapper;
import project.bioinformatics.model.BioUser;
import project.bioinformatics.repository.biouser.BioUserRepository;

@Service
@RequiredArgsConstructor
public class BioUserServiceImpl implements BioUserService {
    private final BioUserRepository bioUserRepository;
    private final BioUserMapper bioUserMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public BioUserResponseDto register(BioUserRegisterRequestDto requestDto)
            throws RegistrationException {
        if (bioUserRepository.findByEmail(requestDto.getEmail()).isPresent()) {
            throw new RegistrationException("User with email: " + requestDto.getEmail()
                    + " already exists");
        }
        BioUser bioUser = bioUserMapper.toBioUser(requestDto);
        bioUser.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        try {
            BioUser savedUser = bioUserRepository.save(bioUser);
            return bioUserMapper.toBioUserResponseDto(savedUser);
        } catch (DataIntegrityViolationException e) {
            throw new RegistrationException("User with email: " + requestDto.getEmail()
                    + " already exists");
        }
    }
}
