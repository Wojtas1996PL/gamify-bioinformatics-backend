package project.bioinformatics.repository.exercise;

import org.springframework.data.jpa.repository.JpaRepository;
import project.bioinformatics.model.Exercise;

public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
}
