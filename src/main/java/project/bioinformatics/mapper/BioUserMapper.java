package project.bioinformatics.mapper;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import project.bioinformatics.config.MapperConfig;
import project.bioinformatics.dto.BioUserRegisterRequestDto;
import project.bioinformatics.dto.BioUserResponseDto;
import project.bioinformatics.model.BioUser;
import project.bioinformatics.model.Role;

@Mapper(config = MapperConfig.class, componentModel = "spring")
public interface BioUserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "authorities", ignore = true)
    @Mapping(target = "roles", expression = "java(mapRoles(bioUserRegisterRequestDto.getRoles()))")
    BioUser toBioUser(BioUserRegisterRequestDto bioUserRegisterRequestDto);

    BioUserResponseDto toBioUserResponseDto(BioUser bioUser);

    default Set<Role> mapRoles(Set<String> roleNames) {
        if (roleNames == null) {
            return new HashSet<>();
        }
        return roleNames.stream()
                .map(name -> {
                    Role role = new Role();
                    role.setName(Role.RoleName.valueOf(name));
                    return role;
                })
                .collect(Collectors.toSet());
    }
}
