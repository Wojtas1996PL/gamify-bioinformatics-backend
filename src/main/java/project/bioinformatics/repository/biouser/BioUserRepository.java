package project.bioinformatics.repository.biouser;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.bioinformatics.model.BioUser;

@Repository
public interface BioUserRepository extends JpaRepository<BioUser, Long> {
    Optional<BioUser> findByEmail(String email);
}
