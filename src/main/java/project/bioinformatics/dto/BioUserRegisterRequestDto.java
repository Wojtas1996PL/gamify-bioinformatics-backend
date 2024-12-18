package project.bioinformatics.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.util.Set;
import lombok.Getter;
import project.bioinformatics.model.Role;

@Getter
public class BioUserRegisterRequestDto {
    @NotNull
    @Size(min = 1, max = 255)
    @Email
    private String email;
    @NotNull
    @Size(min = 1, max = 255)
    private String username;
    @NotNull
    @Size(min = 8, max = 255)
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]+$",
            message = "Password must contain at least one uppercase letter,"
                    + " one digit, and one special character.")
    private String password;
    @NotNull
    private Set<Role.RoleName> roles;
}
