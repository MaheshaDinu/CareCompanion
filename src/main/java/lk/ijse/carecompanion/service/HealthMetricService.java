package lk.ijse.carecompanion.service;

import lk.ijse.carecompanion.dto.HealthMetricChartDTO;
import lk.ijse.carecompanion.dto.HealthMetricDTO;
import lk.ijse.carecompanion.enums.HealthMetricType;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface HealthMetricService {
    void save(HealthMetricDTO healthMetricDTO);

    void update(HealthMetricDTO healthMetricDTO);
    void delete(int id);
    List<HealthMetricDTO> getHealthMetricsByPatientId(int patientId);
    HealthMetricDTO getHealthMetricById(int id);
        HealthMetricChartDTO getChartData(int patientId, HealthMetricType metricType, LocalDate startDate, LocalDate endDate);


    List<HealthMetricDTO> getPatientHealthMetrics(int id, LocalDate startDate, LocalDate endDate, String sort);
}
