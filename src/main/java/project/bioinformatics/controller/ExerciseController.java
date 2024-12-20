package project.bioinformatics.controller;

import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import project.bioinformatics.dto.ExerciseCreateDto;
import project.bioinformatics.dto.ExerciseDto;
import project.bioinformatics.dto.UserExerciseDto;
import project.bioinformatics.service.ExerciseService;
import project.bioinformatics.service.UserExerciseService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/exercises")
public class ExerciseController {
    private final ExerciseService exerciseService;
    private final UserExerciseService userExerciseService;

    @PostMapping
    public ExerciseDto addExercise(@Valid @RequestBody ExerciseCreateDto exerciseCreateDto) {
        System.out.println("Received: " + exerciseCreateDto);
        return exerciseService.addExercise(exerciseCreateDto);
    }

    @GetMapping
    public List<ExerciseDto> getAllExercises(Pageable pageable) {
        return exerciseService.getAllExercises(pageable);
    }

    @GetMapping("/{id}")
    public ExerciseDto getExerciseById(@PathVariable Long id) {
        return exerciseService.getExerciseById(id);
    }

    @PutMapping("/{id}")
    public ExerciseDto updateExerciseById(@PathVariable Long id,
                                          @RequestBody @Valid ExerciseDto exerciseDto) {
        return exerciseService.updateExerciseById(id, exerciseDto);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteExerciseById(@PathVariable Long id) {
        exerciseService.deleteExerciseById(id);
    }

    @PostMapping("/{exerciseId}")
    public UserExerciseDto startUserExercise(@PathVariable int exerciseId) {
        return userExerciseService.startUserExercise(exerciseId);
    }

    @PutMapping("/progress")
    public UserExerciseDto updateExerciseProgress(
            @RequestBody @Valid UserExerciseDto userExerciseDto) {
        return userExerciseService.updateExerciseProgress(userExerciseDto);
    }
}
