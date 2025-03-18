package lk.ijse.carecompanion.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    @NotNull
    private int age;
    @NotBlank
    private String gender;
    @NotNull
    private Date dob;
    private String medicalHistory;
    @NotNull
    private int providerId;

}
