package lk.ijse.carecompanion.controller;

import jakarta.servlet.http.HttpServletRequest;
import lk.ijse.carecompanion.dto.PatientDTO;
import lk.ijse.carecompanion.dto.PatientRegistrationDTO;
import lk.ijse.carecompanion.service.JWTService;
import lk.ijse.carecompanion.service.PatientService;
import lk.ijse.carecompanion.service.ProviderService;
import lk.ijse.carecompanion.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/patient/profile")
public class PatientProfileController {
    @Autowired
    PatientService patientService;
    @Autowired
    ProviderService providerService;
    @Autowired
    JWTService jwtService;

    @GetMapping("/getPatient")
    public ResponseEntity<ResponseUtil> getPatient(HttpServletRequest request){
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ResponseUtil(401,"Unauthorized",null));
        }

        String token = authHeader.substring(7);
        String username = jwtService.extractUsername(token);

        PatientDTO patientDTO = patientService.getByUserName(username);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseUtil(200,"success",patientDTO));

    }
    @GetMapping("/getProvider/{id}")
    public ResponseEntity<ResponseUtil> getProvider(@PathVariable int id) {return ResponseEntity.status(HttpStatus.OK).body(new ResponseUtil(200,"success",providerService.getById(id)));}

    @PutMapping("/update")
    public ResponseEntity<ResponseUtil> updatePatient(@RequestBody PatientRegistrationDTO patientRegistrationDTO){
        try {
            patientService.update(patientRegistrationDTO);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseUtil(200,"Patient Updated Successfully",null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseUtil(500,e.getMessage(),null));
        }
    }

}

