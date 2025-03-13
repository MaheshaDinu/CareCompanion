package lk.ijse.carecompanion.repository;

import lk.ijse.carecompanion.entity.PatientThreshold;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientThresholdRepo extends JpaRepository<PatientThreshold,Integer> {
    List<PatientThreshold> findByPatientId(int patientId);
}
