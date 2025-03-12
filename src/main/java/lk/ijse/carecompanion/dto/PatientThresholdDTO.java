package lk.ijse.carecompanion.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PatientThresholdDTO {
    private int id;
    private String metricType; // e.g., "BLOOD_PRESSURE", "BLOOD_SUGAR", etc.
    private Double minValue;
    private Double maxValue;
    private Long patientId;
}
