package project.bioinformatics.repository.exercise;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import project.bioinformatics.model.UserExercise;

public interface UserExerciseRepository extends JpaRepository<UserExercise, Long> {
    Optional<UserExercise> findByUserIdAndExerciseId(Long userId, int exerciseId);
}
