package lk.ijse.carecompanion.repository;

import lk.ijse.carecompanion.entity.Alert;
import lk.ijse.carecompanion.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlertRepo extends JpaRepository<Alert,Integer> {
    List<Alert> findByRecipientOrderByTimestampDesc(Users recipient);
}
