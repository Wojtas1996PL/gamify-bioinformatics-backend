package project.bioinformatics.dto;

import lombok.Getter;

@Getter
public class UserExerciseDto {
    private Long id;
    private Long userId;
    private int exerciseId;
    private int progress;
}
