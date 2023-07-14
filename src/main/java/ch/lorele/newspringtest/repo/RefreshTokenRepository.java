package ch.lorele.newspringtest.repo;

import ch.lorele.newspringtest.model.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, String> {
    boolean existsByName(String name);

    void deleteByName(String name);
}
