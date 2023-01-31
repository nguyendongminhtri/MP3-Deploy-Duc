package ducmin.demo.service;

import ducmin.demo.model.Role;
import ducmin.demo.model.RoleName;

import java.util.Optional;

public interface IRoleService {
    Optional<Role> findByName(RoleName name);
}
