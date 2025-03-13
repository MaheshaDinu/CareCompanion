package lk.ijse.carecompanion.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Data
public class PatientRegistrationDTO extends UserRegistrationDTO{
    @NotBlank
    private int age;
    @NotBlank
    private String gender;
    @NotBlank
    private Date dob;
    private String medicalHistory;
    @NotBlank
    private int providerId;

}
