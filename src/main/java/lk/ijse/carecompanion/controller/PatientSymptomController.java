package lk.ijse.carecompanion.controller;

import jakarta.servlet.http.HttpServletRequest;
import lk.ijse.carecompanion.dto.PatientDTO;
import lk.ijse.carecompanion.dto.SymptomDTO;
import lk.ijse.carecompanion.service.JWTService;
import lk.ijse.carecompanion.service.PatientService;
import lk.ijse.carecompanion.service.SymptomService;
import lk.ijse.carecompanion.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/patient/symptom")
public class PatientSymptomController {
    @Autowired
    PatientService patientService;
    @Autowired
    JWTService jwtService;
    @Autowired
    SymptomService symptomService;


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
    @PostMapping("/addSymptom")
    public ResponseEntity<ResponseUtil> addSymptom(@RequestBody SymptomDTO symptomDTO){
        try {
            symptomService.save(symptomDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseUtil(201,"Symptom Added Successfully",null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseUtil(500,e.getMessage(),null));
        }
    }
}
