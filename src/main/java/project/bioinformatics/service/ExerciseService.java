package project.bioinformatics.service;

import java.util.List;
import org.springframework.data.domain.Pageable;
import project.bioinformatics.dto.ExerciseCreateDto;
import project.bioinformatics.dto.ExerciseDto;

public interface ExerciseService {
    ExerciseDto addExercise(ExerciseCreateDto exerciseCreateDto);

    List<ExerciseDto> getAllExercises(Pageable pageable);

    ExerciseDto getExerciseById(Long id);

    ExerciseDto updateExerciseById(Long id, ExerciseDto exerciseDto);

    void deleteExerciseById(Long id);
}
