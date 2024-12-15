package project.bioinformatics.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UserExerciseDto {
    private Long id;
    private Long userId;
    private int exerciseId;
    private int progress;
}
