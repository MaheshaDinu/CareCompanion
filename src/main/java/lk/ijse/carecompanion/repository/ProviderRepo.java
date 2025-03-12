package lk.ijse.carecompanion.repository;

import lk.ijse.carecompanion.entity.Provider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProviderRepo extends JpaRepository<Provider, Integer> {
    Optional<Provider> findByUserName(String userName);
}
