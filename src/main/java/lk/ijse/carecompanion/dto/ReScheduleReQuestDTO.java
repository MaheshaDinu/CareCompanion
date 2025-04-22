package lk.ijse.carecompanion.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReScheduleReQuestDTO {
    private LocalDateTime dateTime;
    private String notes;
    private boolean notifyPatient;
}
