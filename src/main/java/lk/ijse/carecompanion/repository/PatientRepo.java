package lk.ijse.carecompanion.repository;

import lk.ijse.carecompanion.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PatientRepo extends JpaRepository<Patient,Integer> {
    Optional<Patient> findByUserName(String userName);
}
