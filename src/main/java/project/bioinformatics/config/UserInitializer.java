package project.bioinformatics.config;

import java.util.Optional;
import java.util.Set;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import project.bioinformatics.model.BioUser;
import project.bioinformatics.model.Role;
import project.bioinformatics.repository.biouser.BioUserRepository;
import project.bioinformatics.repository.role.RoleRepository;

@Configuration
public class UserInitializer {

    @Bean
    public CommandLineRunner init(BioUserRepository userRepository, RoleRepository roleRepository,
                                  PasswordEncoder passwordEncoder) {
        return args -> {
            Optional<Role> adminRoleOpt = roleRepository.findByName(Role.RoleName.ROLE_ADMIN);
            Role adminRole = adminRoleOpt.orElseGet(() -> {
                Role newRole = new Role();
                newRole.setName(Role.RoleName.ROLE_ADMIN);
                return roleRepository.save(newRole);
            });

            if (userRepository.findByUsername("admin").isEmpty()) {
                BioUser user = new BioUser();
                user.setUsername("admin");
                user.setEmail("admin@example.com");
                user.setPassword(passwordEncoder.encode("admin123"));
                user.setRoles(Set.of(adminRole));
                user.setName("Admin");
                user.setPhoto(null);
                user.setScorePoints(0);
                userRepository.save(user);
            }
        };
    }
}
