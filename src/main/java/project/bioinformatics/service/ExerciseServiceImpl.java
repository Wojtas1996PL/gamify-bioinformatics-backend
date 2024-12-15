package project.bioinformatics.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import project.bioinformatics.dto.ExerciseCreateDto;
import project.bioinformatics.dto.ExerciseDto;
import project.bioinformatics.exception.EntityNotFoundException;
import project.bioinformatics.mapper.ExerciseMapper;
import project.bioinformatics.model.Exercise;
import project.bioinformatics.repository.exercise.ExerciseRepository;

@Service
@RequiredArgsConstructor
public class ExerciseServiceImpl implements ExerciseService {
    private final ExerciseRepository exerciseRepository;
    private final ExerciseMapper exerciseMapper;

    @Override
    public ExerciseDto addExercise(ExerciseCreateDto exerciseCreateDto) {
        Exercise exercise = exerciseMapper.toExercise(exerciseCreateDto);
        Exercise savedExercise = exerciseRepository.save(exercise);
        return exerciseMapper.toDto(savedExercise);
    }

    @Override
    public List<ExerciseDto> getAllExercises(Pageable pageable) {
        return exerciseRepository.findAll(pageable).stream().map(exerciseMapper::toDto).toList();
    }

    @Override
    public ExerciseDto getExerciseById(Long id) {
        return exerciseMapper.toDto(exerciseRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Exercise with id " + id + " not found")
        ));
    }

    @Override
    public ExerciseDto updateExerciseById(Long id, ExerciseDto exerciseDto) {
        Exercise existingExercise = exerciseRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Exercise with id " + id + " not found")
        );
        if (exerciseDto.getTitle() != null && !exerciseDto.getTitle().isEmpty()) {
            existingExercise.setTitle(exerciseDto.getTitle());
        }
        if (exerciseDto.getTopic() != null && !exerciseDto.getTopic().isEmpty()) {
            existingExercise.setTopic(exerciseDto.getTopic());
        }
        if (exerciseDto.getDescription() != null && !exerciseDto.getDescription().isEmpty()) {
            existingExercise.setDescription(exerciseDto.getDescription());
        }
        if (exerciseDto.getSteps() > 0) {
            existingExercise.setSteps(exerciseDto.getSteps());
        }
        if (exerciseDto.getTime() > 0) {
            existingExercise.setTime(exerciseDto.getTime());
        }
        return exerciseMapper.toDto(exerciseRepository.save(existingExercise));
    }

    @Override
    public void deleteExerciseById(Long id) {
        if (!exerciseRepository.existsById(id)) {
            throw new EntityNotFoundException("Exercise with id " + id + " not found");
        }
        exerciseRepository.deleteById(id);
    }
}
