package lk.ijse.carecompanion.service;

import lk.ijse.carecompanion.dto.HealthMetricDTO;

public interface HealthMetricService {
    void save(HealthMetricDTO healthMetricDTO);
    void update(HealthMetricDTO healthMetricDTO);
    void delete(int id);
}
