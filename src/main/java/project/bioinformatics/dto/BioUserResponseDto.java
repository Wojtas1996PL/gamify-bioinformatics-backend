package project.bioinformatics.dto;

import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BioUserResponseDto {
    private Long id;
    private String email;
    private String username;
    private String name;
    private int scorePoints;
    private Set<String> roles;
    private String photo;
}
