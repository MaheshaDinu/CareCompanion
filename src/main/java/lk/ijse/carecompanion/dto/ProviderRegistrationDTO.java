package lk.ijse.carecompanion.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Data
public class ProviderRegistrationDTO extends UserRegistrationDTO {
    @NotBlank
    private String clinicName;
    @NotBlank
    private String licenseNumber;

}
