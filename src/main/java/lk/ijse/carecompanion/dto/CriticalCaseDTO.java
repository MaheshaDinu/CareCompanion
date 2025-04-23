package lk.ijse.carecompanion.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CriticalCaseDTO {
    private PatientDTO patient;
    private String metricType;
    private Double metricValue;
}
