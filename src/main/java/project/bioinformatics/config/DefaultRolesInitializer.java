package project.bioinformatics.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import project.bioinformatics.model.Role;
import project.bioinformatics.repository.role.RoleRepository;

@Component
public class DefaultRolesInitializer implements CommandLineRunner {
    private final RoleRepository roleRepository;

    public DefaultRolesInitializer(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) {
        if (roleRepository.findByName(Role.RoleName.ROLE_USER).isEmpty()) {
            Role role = new Role();
            role.setName(Role.RoleName.ROLE_USER);
            roleRepository.save(role);
        }
        if (roleRepository.findByName(Role.RoleName.ROLE_ADMIN).isEmpty()) {
            Role role = new Role();
            role.setName(Role.RoleName.ROLE_ADMIN);
            roleRepository.save(role);
        }
    }
}
