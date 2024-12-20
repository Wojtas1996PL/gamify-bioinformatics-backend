package project.bioinformatics.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserExerciseDto {
    private Long id;
    private Long userId;
    @NotNull
    @Min(1)
    private int exerciseId;
    @NotNull
    @Min(0)
    @Max(100)
    private int progress;
}
