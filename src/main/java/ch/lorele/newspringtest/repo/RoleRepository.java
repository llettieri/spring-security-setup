package ch.lorele.newspringtest.repo;

import ch.lorele.newspringtest.model.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, String> {
}
