package project.bioinformatics.repository.exercise;

import org.springframework.data.jpa.repository.JpaRepository;
import project.bioinformatics.model.UserExercise;

public interface UserExerciseRepository extends JpaRepository<UserExercise, Long> {
}
