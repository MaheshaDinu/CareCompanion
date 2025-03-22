package lk.ijse.carecompanion.dto;

import lk.ijse.carecompanion.enums.HealthMetricType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class HealthMetricDTO {
    private int id;
    private HealthMetricType type; // e.g., "BLOOD_PRESSURE", "HEART_RATE", etc.
    private Double value;
    private Integer systolic;
    private Integer diastolic;
    private LocalDateTime timestamp;
    private String notes;
    private int patientId;
}
