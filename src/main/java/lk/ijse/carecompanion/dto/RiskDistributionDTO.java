package lk.ijse.carecompanion.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RiskDistributionDTO {
    private int lowRisk;
    private int mediumRisk;
    private int highRisk;
}
