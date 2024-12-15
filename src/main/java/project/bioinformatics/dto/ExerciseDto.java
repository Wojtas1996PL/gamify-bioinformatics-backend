package project.bioinformatics.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExerciseDto {
    private Long id;
    @Size(max = 20)
    private String title;
    @Size(max = 20)
    private String topic;
    @Size(max = 100)
    private String description;
    @Max(50)
    private int steps;
    private int time;
}
