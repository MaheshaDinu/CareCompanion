package lk.ijse.carecompanion.dto;

import lk.ijse.carecompanion.enums.Severity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SymptomDTO {
    private int id;
    private String description;
    private Severity severity; // e.g., "LOW", "MEDIUM", "HIGH"
    private LocalDateTime date;
    private String notes;
    private int patientId;
}
