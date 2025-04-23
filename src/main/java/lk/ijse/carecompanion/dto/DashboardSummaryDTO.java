package lk.ijse.carecompanion.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DashboardSummaryDTO {
    private int upcomingAppointmentsCount;
    private int todayAppointmentsCount;
    private int patientCount;
    private int newPatientsCount;
    private int criticalCasesCount;
}
