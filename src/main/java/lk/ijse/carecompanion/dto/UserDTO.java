package lk.ijse.carecompanion.dto;

import lk.ijse.carecompanion.enums.Role;
import lombok.*;

import java.time.LocalDateTime;
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class UserDTO {
    private int id;
    private String userName;
    private String email;
    private String firstName;
    private String lastName;
    private String address;
    private String phoneNumber;
    private LocalDateTime createdAt;
    private Role role;
}
