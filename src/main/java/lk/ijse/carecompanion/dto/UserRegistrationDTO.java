package lk.ijse.carecompanion.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lk.ijse.carecompanion.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public abstract class UserRegistrationDTO {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    @Email
    @NotBlank
    private String email;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotBlank
    private String phoneNumber;
    @NotBlank
    private Role role;
}
