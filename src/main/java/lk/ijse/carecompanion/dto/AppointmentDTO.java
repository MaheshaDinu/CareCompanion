package lk.ijse.carecompanion.dto;

import lk.ijse.carecompanion.enums.AppointmentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AppointmentDTO {
    private int id;
    private LocalDateTime dateTime;
    private AppointmentStatus status; // e.g., "REQUESTED", "CONFIRMED", "CANCELLED"
    private String purpose;
    // For simplicity, include only IDs for related users
    private Long patientId;
    private Long providerId;
}
