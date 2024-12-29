package project.bioinformatics.service;

import project.bioinformatics.dto.BioUserLoginRequestDto;
import project.bioinformatics.dto.BioUserRegisterRequestDto;
import project.bioinformatics.dto.BioUserResponseDto;
import project.bioinformatics.exception.RegistrationException;

public interface BioUserService {
    BioUserLoginRequestDto register(BioUserRegisterRequestDto requestDto)
            throws RegistrationException;

    BioUserResponseDto getUserInfo();
}
