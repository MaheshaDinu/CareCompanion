package lk.ijse.carecompanion.controller;

import jakarta.validation.Valid;
import lk.ijse.carecompanion.dto.PatientRegistrationDTO;
import lk.ijse.carecompanion.dto.ProviderRegistrationDTO;
import lk.ijse.carecompanion.service.PatientService;
import lk.ijse.carecompanion.service.ProviderService;
import lk.ijse.carecompanion.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/register")
@CrossOrigin(origins = "http://localhost:63342")
public class RegistrationController {
    @Autowired
    PatientService patientService;
    @Autowired
    ProviderService providerService;

    @PostMapping("/provider")
    public ResponseEntity<ResponseUtil> registerProvider(@Valid @RequestBody ProviderRegistrationDTO providerRegistrationDTO){
        try {
            providerService.register(providerRegistrationDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseUtil(201,"Provider Registered Successfully",null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseUtil(500,e.getMessage(),null));
        }

    }
    @PostMapping("/patient")
    public ResponseEntity<ResponseUtil> registerPatient(@Valid @RequestBody PatientRegistrationDTO patientRegistrationDTO){
        try {
            patientService.register(patientRegistrationDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseUtil(201,"Patient Registered Successfully",null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseUtil(500,e.getMessage(),null));
        }

    }
}
