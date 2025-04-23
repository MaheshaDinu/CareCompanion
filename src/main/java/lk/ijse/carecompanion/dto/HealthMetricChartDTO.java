package lk.ijse.carecompanion.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HealthMetricChartDTO {
    private List<String> labels;
    private List<Double> systolic;
    private List<Double> diastolic;
}
