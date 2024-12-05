package project.bioinformatics.repository.role;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.bioinformatics.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}
