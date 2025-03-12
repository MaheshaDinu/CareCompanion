package lk.ijse.carecompanion.repository;

import lk.ijse.carecompanion.entity.HealthMetric;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HealthMetricRepo extends JpaRepository<HealthMetric, Integer> {
}
