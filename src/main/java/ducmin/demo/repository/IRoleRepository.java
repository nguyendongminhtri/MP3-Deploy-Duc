package ducmin.demo.repository;

import ducmin.demo.model.Role;
import ducmin.demo.model.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IRoleRepository extends JpaRepository<Role, Long> {
   Optional<Role> findByName(RoleName name);
}
