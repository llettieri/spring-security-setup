package ch.lorele.newspringtest.service.impl;

import ch.lorele.newspringtest.model.entity.Role;
import ch.lorele.newspringtest.repo.RoleRepository;
import ch.lorele.newspringtest.service.RoleService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class RoleServiceImpl implements RoleService {
    private RoleRepository roleRepository;

    @Override
    public Role createRole(String role) {
        Role r = this.roleRepository.save(Role.builder().name(role).build());
        log.info("[RoleService] - Role '{}' successfully created", role);
        return r;
    }

    @Override
    public void deleteRole(String role) {
        this.roleRepository.deleteById(role);
        log.info("[RoleService] - Role '{}' successfully deleted", role);
    }
}
