package lk.ijse.carecompanion.controller;

import lk.ijse.carecompanion.dto.AuthTokenDTO;
import lk.ijse.carecompanion.dto.UserLoginDTO;
import lk.ijse.carecompanion.enums.Role;
import lk.ijse.carecompanion.service.PatientService;
import lk.ijse.carecompanion.service.ProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth/login")
public class AuthController {
    @Autowired
    PatientService patientService;
    @Autowired
    ProviderService providerService;
    @PostMapping("")
    public ResponseEntity<AuthTokenDTO> login(@RequestBody UserLoginDTO userDTO) {

        if (userDTO.getRole().equals(Role.PATIENT)){
            return ResponseEntity.ok(patientService.verifyPatient(userDTO));

        } else {
            return ResponseEntity.ok(providerService.verifyProvider(userDTO));
        }

    }
}
