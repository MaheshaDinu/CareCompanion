package lk.ijse.carecompanion.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MedicationAdherenceDTO {
    private List<String> categories;
    private List<Double> adherenceRates;
}
