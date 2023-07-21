package ch.lorele.newspringtest.repo;

import ch.lorele.newspringtest.model.entity.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RoleRepository extends MongoRepository<Role, String> {
    boolean existsByName(String name);
}
