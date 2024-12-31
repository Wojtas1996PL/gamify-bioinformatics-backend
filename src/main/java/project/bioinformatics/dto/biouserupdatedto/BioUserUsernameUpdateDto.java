package project.bioinformatics.dto.biouserupdatedto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class BioUserUsernameUpdateDto {
    @NotNull
    @Size(min = 1, max = 255)
    private String username;
}
