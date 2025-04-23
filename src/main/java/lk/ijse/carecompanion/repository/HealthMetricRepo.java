package lk.ijse.carecompanion.repository;

import lk.ijse.carecompanion.dto.HealthMetricChartDTO;
import lk.ijse.carecompanion.entity.HealthMetric;
import lk.ijse.carecompanion.enums.HealthMetricType;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface HealthMetricRepo extends JpaRepository<HealthMetric, Integer> {
    List<HealthMetric> findByPatientId(int patientId);
    List<HealthMetric> findByPatient_IdAndTimestampBetween(
            int patientId,
            LocalDateTime start,
            LocalDateTime end,
            Sort sort
    );
    List<HealthMetric> findByPatient_IdAndTypeAndTimestampBetween(
            int patientId,
            HealthMetricType type,
            LocalDateTime start,
            LocalDateTime end,
            Sort sort
    );

    HealthMetric findTopByPatientIdAndTypeOrderByTimestampDesc(int id, HealthMetricType type);
}
