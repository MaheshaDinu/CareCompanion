package lk.ijse.carecompanion.dto;

import lk.ijse.carecompanion.enums.AppointmentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AppointmentResponseDTO {
    private int id;
    private LocalDateTime dateTime;
    private AppointmentStatus status; // e.g., "REQUESTED", "CONFIRMED", "CANCELLED"
    private String purpose;
    private int patientId;
    private String patientName;
    private int providerId;
}
