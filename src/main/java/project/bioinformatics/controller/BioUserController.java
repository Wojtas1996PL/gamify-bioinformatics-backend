package project.bioinformatics.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.bioinformatics.dto.BioUserResponseDto;
import project.bioinformatics.dto.biouserupdatedto.BioUserNameUpdateDto;
import project.bioinformatics.dto.biouserupdatedto.BioUserPhotoUpdateDto;
import project.bioinformatics.dto.biouserupdatedto.BioUserScorePointsUpdateDto;
import project.bioinformatics.dto.biouserupdatedto.BioUserUpdateDto;
import project.bioinformatics.dto.biouserupdatedto.BioUserUsernameUpdateDto;
import project.bioinformatics.service.BioUserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class BioUserController {
    private final BioUserService bioUserService;

    @GetMapping
    public BioUserResponseDto userInfo() {
        return bioUserService.getUserInfo();
    }

    @PutMapping("/username")
    public BioUserResponseDto changeUsername(
            @Valid @RequestBody BioUserUsernameUpdateDto usernameUpdateDto) {
        return bioUserService.changeUsername(usernameUpdateDto);
    }

    @PutMapping("/name")
    public BioUserResponseDto changeName(
            @Valid @RequestBody BioUserNameUpdateDto nameUpdateDto) {
        return bioUserService.changeName(nameUpdateDto);
    }

    @PutMapping("/photo")
    public BioUserResponseDto changePhoto(
            @Valid @RequestBody BioUserPhotoUpdateDto photoUpdateDto) {
        return bioUserService.changePhoto(photoUpdateDto);
    }

    @PutMapping("/score-points")
    public BioUserResponseDto addScorePoints(
            @Valid @RequestBody BioUserScorePointsUpdateDto scorePointsUpdateDto) {
        return bioUserService.addScorePoints(scorePointsUpdateDto);
    }

    @PutMapping("/update")
    public BioUserResponseDto updateUser(@Valid @RequestBody BioUserUpdateDto updateDto) {
        return bioUserService.updateUser(updateDto);
    }
}
