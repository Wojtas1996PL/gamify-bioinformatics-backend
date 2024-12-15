package project.bioinformatics.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import project.bioinformatics.dto.UserExerciseDto;
import project.bioinformatics.mapper.UserExerciseMapper;
import project.bioinformatics.model.BioUser;
import project.bioinformatics.model.UserExercise;
import project.bioinformatics.repository.biouser.BioUserRepository;
import project.bioinformatics.repository.exercise.UserExerciseRepository;

@Service
@RequiredArgsConstructor
public class UserExerciseImpl implements UserExerciseService {
    private static final int DEFAULT_EXERCISE_PROGRESS = 0;

    private final BioUserRepository bioUserRepository;
    private final UserExerciseRepository userExerciseRepository;
    private final UserExerciseMapper userExerciseMapper;

    @Override
    public UserExerciseDto startUserExercise(int exerciseId) {
        UserExercise userExercise = new UserExercise();
        userExercise.setUserId(getAuthenticatedUser().getId());
        userExercise.setExerciseId(exerciseId);
        userExercise.setProgress(DEFAULT_EXERCISE_PROGRESS);
        return userExerciseMapper.toUserExerciseDto(userExerciseRepository.save(userExercise));
    }

    private BioUser getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return bioUserRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
