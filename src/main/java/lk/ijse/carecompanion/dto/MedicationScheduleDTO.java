package lk.ijse.carecompanion.dto;

import lk.ijse.carecompanion.enums.Frequency;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class MedicationScheduleDTO {
    private int id;
    private String medicationName;
    private String dosage;
    private Frequency frequency; // e.g., "DAILY", "TWICE_DAILY"
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalTime reminderTime;
    private int patientId;
}
