package lk.ijse.carecompanion.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AlertDTO {
    private int id;

    private LocalDateTime dateTime;
    private String status; // e.g., "REQUESTED", "CONFIRMED", "CANCELLED"
    private String purpose;
    // For simplicity, include only IDs for related users
    private Long patientId;
    private Long providerId;
}
