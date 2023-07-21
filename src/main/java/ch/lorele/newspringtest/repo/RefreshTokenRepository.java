package ch.lorele.newspringtest.repo;

import ch.lorele.newspringtest.model.entity.RefreshToken;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RefreshTokenRepository extends MongoRepository<RefreshToken, String> {
    boolean existsByName(String name);

    void deleteByName(String name);
}
