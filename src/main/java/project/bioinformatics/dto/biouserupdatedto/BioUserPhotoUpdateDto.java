package project.bioinformatics.dto.biouserupdatedto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class BioUserPhotoUpdateDto {
    @NotNull
    @Size(min = 1, max = 255)
    @Pattern(
            regexp = "^(https?://)[^\\s/$.?#].[^\\s]*$",
            message = "The photo URL must be a valid URL starting with http:// or https://"
    )
    private String photo;
}
