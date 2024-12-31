package project.bioinformatics.dto.biouserupdatedto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class BioUserScorePointsUpdateDto {
    @NotNull
    @Min(1)
    @Max(255)
    private int scorePoints;
}
