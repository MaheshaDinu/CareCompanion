package lk.ijse.carecompanion.service;

import lk.ijse.carecompanion.dto.HealthMetricDTO;

import java.util.List;

public interface HealthMetricService {
    void save(HealthMetricDTO healthMetricDTO);

    void update(HealthMetricDTO healthMetricDTO);
    void delete(int id);
    List<HealthMetricDTO> getHealthMetricsByPatientId(int patientId);
    HealthMetricDTO getHealthMetricById(int id);
}
