package ducmin.demo.service.impl;

import ducmin.demo.model.Role;
import ducmin.demo.model.RoleName;
import ducmin.demo.repository.IRoleRepository;
import ducmin.demo.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleServiceImpl implements IRoleService {
    @Autowired
    IRoleRepository roleRepository;
    @Override
    public Optional<Role> findByName(RoleName name) {
        return roleRepository.findByName(name);
    }
}
