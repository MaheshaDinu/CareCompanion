package lk.ijse.carecompanion.repository;

import lk.ijse.carecompanion.entity.HealthMetric;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HealthMetricRepo extends JpaRepository<HealthMetric, Integer> {
    List<HealthMetric> findByPatientId(int patientId);
}
