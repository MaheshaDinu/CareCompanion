package lk.ijse.carecompanion.repository;

import lk.ijse.carecompanion.entity.Symptom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SymptomRepo extends JpaRepository<Symptom, Integer> {
    List<Symptom> findByPatientId(int patientId);
}
