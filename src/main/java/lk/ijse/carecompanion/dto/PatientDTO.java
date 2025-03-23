package lk.ijse.carecompanion.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PatientDTO extends UserDTO{
    private int age;
    private String gender;
    private Date dob;
    private String medicalHistory;
    private List<HealthMetricDTO> healthMetrics;
    private List<SymptomDTO> symptoms;
    private List<MedicationScheduleDTO> medicationSchedules;
    private List<AppointmentDTO> appointmentsAsPatient;
    private List<PatientThresholdDTO> thresholds;
    private int providerId;
}
