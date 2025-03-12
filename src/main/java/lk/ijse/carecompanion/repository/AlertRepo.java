package lk.ijse.carecompanion.repository;

import lk.ijse.carecompanion.entity.Alert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlertRepo extends JpaRepository<Alert,Integer> {
}
