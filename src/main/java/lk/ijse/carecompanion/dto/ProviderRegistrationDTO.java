package lk.ijse.carecompanion.dto;

import jakarta.validation.constraints.NotBlank;

public class ProviderRegistrationDTO extends UserRegistrationDTO {
    @NotBlank
    private String clinicName;
    @NotBlank
    private String licenseNumber;

}
