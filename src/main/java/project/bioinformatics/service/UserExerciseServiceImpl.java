package project.bioinformatics.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import project.bioinformatics.dto.UserExerciseDto;
import project.bioinformatics.exception.EntityNotFoundException;
import project.bioinformatics.mapper.UserExerciseMapper;
import project.bioinformatics.model.BioUser;
import project.bioinformatics.model.UserExercise;
import project.bioinformatics.repository.biouser.BioUserRepository;
import project.bioinformatics.repository.exercise.ExerciseRepository;
import project.bioinformatics.repository.exercise.UserExerciseRepository;

@Service
@RequiredArgsConstructor
public class UserExerciseServiceImpl implements UserExerciseService {
    private static final int DEFAULT_EXERCISE_PROGRESS = 0;

    private final BioUserRepository bioUserRepository;
    private final UserExerciseRepository userExerciseRepository;
    private final UserExerciseMapper userExerciseMapper;
    private final ExerciseRepository exerciseRepository;

    @Override
    public UserExerciseDto startUserExercise(int exerciseId) {
        Long exerciseIdAsLong = (long) exerciseId;

        if (!exerciseRepository.existsById(exerciseIdAsLong)) {
            throw new EntityNotFoundException("Exercise with ID "
                    + exerciseId + " not found.");
        }

        BioUser authenticatedUser = getAuthenticatedUser();
        UserExercise usersExercise = userExerciseRepository.findByUserIdAndExerciseId(
                authenticatedUser.getId(), exerciseId)
                .orElseGet(() -> {
                    UserExercise userExercise = new UserExercise();
                    userExercise.setUserId(getAuthenticatedUser().getId());
                    userExercise.setExerciseId(exerciseId);
                    userExercise.setProgress(DEFAULT_EXERCISE_PROGRESS);
                    return userExerciseRepository.save(userExercise);
                });
        return userExerciseMapper.toUserExerciseDto(userExerciseRepository.save(usersExercise));
    }

    @Override
    public UserExerciseDto updateExerciseProgress(UserExerciseDto userExerciseDto) {
        BioUser authenticatedUser = getAuthenticatedUser();
        UserExercise usersExercise = userExerciseRepository.findByUserIdAndExerciseId(
                authenticatedUser.getId(), userExerciseDto.getExerciseId())
                .orElseGet(() -> {
                    UserExercise newUserExercise = new UserExercise();
                    newUserExercise.setUserId(authenticatedUser.getId());
                    newUserExercise.setExerciseId(userExerciseDto.getExerciseId());
                    newUserExercise.setProgress(DEFAULT_EXERCISE_PROGRESS);
                    return newUserExercise;
                });

        usersExercise.setProgress(userExerciseDto.getProgress());
        return userExerciseMapper.toUserExerciseDto(userExerciseRepository.save(usersExercise));
    }

    private BioUser getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return bioUserRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
