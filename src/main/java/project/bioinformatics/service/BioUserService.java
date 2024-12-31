package project.bioinformatics.service;

import project.bioinformatics.dto.BioUserLoginRequestDto;
import project.bioinformatics.dto.BioUserRegisterRequestDto;
import project.bioinformatics.dto.BioUserResponseDto;
import project.bioinformatics.dto.biouserupdatedto.BioUserNameUpdateDto;
import project.bioinformatics.dto.biouserupdatedto.BioUserPhotoUpdateDto;
import project.bioinformatics.dto.biouserupdatedto.BioUserScorePointsUpdateDto;
import project.bioinformatics.dto.biouserupdatedto.BioUserUsernameUpdateDto;
import project.bioinformatics.exception.RegistrationException;

public interface BioUserService {
    BioUserLoginRequestDto register(BioUserRegisterRequestDto requestDto)
            throws RegistrationException;

    BioUserResponseDto getUserInfo();

    BioUserResponseDto changeUsername(BioUserUsernameUpdateDto usernameUpdateDto);

    BioUserResponseDto changeName(BioUserNameUpdateDto nameUpdateDto);

    BioUserResponseDto changePhoto(BioUserPhotoUpdateDto photoUpdateDto);

    BioUserResponseDto addScorePoints(BioUserScorePointsUpdateDto scorePointsUpdateDto);
}
