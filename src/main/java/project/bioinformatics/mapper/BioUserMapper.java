package project.bioinformatics.mapper;

import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import project.bioinformatics.config.MapperConfig;
import project.bioinformatics.dto.BioUserLoginRequestDto;
import project.bioinformatics.dto.BioUserRegisterRequestDto;
import project.bioinformatics.dto.BioUserResponseDto;
import project.bioinformatics.model.BioUser;
import project.bioinformatics.model.Role;

@Mapper(config = MapperConfig.class, componentModel = "spring")
public interface BioUserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "authorities", ignore = true)
    @Mapping(source = "username", target = "username")
    @Mapping(source = "roles", target = "roles")
    BioUser toBioUser(BioUserRegisterRequestDto bioUserRegisterRequestDto);

    @Mapping(target = "username", expression = "java(bioUser.getUsernameField())")
    @Mapping(target = "roles", expression = "java(mapRolesToRoleNames(bioUser.getRoles()))")
    BioUserResponseDto toBioUserResponseDto(BioUser bioUser);

    @Mapping(source = "email", target = "email")
    @Mapping(source = "password", target = "password")
    BioUserLoginRequestDto toBioUserLoginRequestDto(BioUserRegisterRequestDto bioUser);

    default Set<Role> mapRoles(Set<Role.RoleName> roleNames) {
        if (roleNames == null) {
            return null;
        }
        return roleNames.stream()
                .map(roleName -> {
                    Role role = new Role();
                    role.setName(roleName);
                    return role;
                })
                .collect(Collectors.toSet());
    }

    default Set<String> mapRolesToRoleNames(Set<Role> roles) {
        if (roles == null) {
            return null;
        }
        return roles.stream()
                .map(role -> role.getName().name())
                .collect(Collectors.toSet());
    }
}
