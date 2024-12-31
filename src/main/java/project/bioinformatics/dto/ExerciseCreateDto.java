package project.bioinformatics.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExerciseCreateDto {
    @NotNull
    @Size(min = 1, max = 150)
    private String title;
    @NotNull
    @Size(min = 1, max = 200)
    private String topic;
    @NotNull
    @Size(min = 1, max = 500)
    private String description;
    @NotNull
    @Min(1)
    @Max(50)
    private int steps;
    @NotNull
    @Min(1)
    private int time;
}
