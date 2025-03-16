package lk.ijse.carecompanion.dto;

import lk.ijse.carecompanion.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserLoginDTO {
    private String username;
    private String password;
    private Role role;
}
