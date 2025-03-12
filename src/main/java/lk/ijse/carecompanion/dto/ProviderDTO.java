package lk.ijse.carecompanion.dto;

import lombok.*;

import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ProviderDTO extends UserDTO{
    private String clinicName;
    private String licenseNumber;
    private List<AppointmentDTO> appointmentsAsProvider;
    private List<PatientDTO> patients;
}
