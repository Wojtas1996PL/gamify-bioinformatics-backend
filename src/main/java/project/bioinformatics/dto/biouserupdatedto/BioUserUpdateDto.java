package project.bioinformatics.dto.biouserupdatedto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class BioUserUpdateDto {

    @Size(min = 1, max = 255)
    private String name;
    @Size(min = 1, max = 255)
    @Pattern(
            regexp = "^(https?://)[^\\s/$.?#].[^\\s]*$",
            message = "The photo URL must be a valid URL starting with http:// or https://"
    )
    private String photo;
    @Size(min = 1, max = 255)
    private String username;
    @Size(min = 8, max = 255)
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]+$",
            message = "Password must contain at least one uppercase letter,"
                    + " one digit, and one special character.")
    private String password;
    @Size(min = 1, max = 255)
    @Email
    private String email;
}

