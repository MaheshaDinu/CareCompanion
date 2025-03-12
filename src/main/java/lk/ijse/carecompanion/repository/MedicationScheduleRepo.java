package lk.ijse.carecompanion.repository;

import lk.ijse.carecompanion.entity.MedicationSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicationScheduleRepo extends JpaRepository<MedicationSchedule,Integer> {
}
