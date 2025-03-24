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

import java.util.List;

@RestController
@RequestMapping("/api/patient/symptom")
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
    @GetMapping("getById/{id}")
    public ResponseEntity<ResponseUtil> getSymptom(@RequestParam int id){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseUtil(200, "", symptomService.getSymptomById(id)));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseUtil(500,e.getMessage(),null));
        }

    }
    @GetMapping("/getAll/{patientId}")
    public ResponseEntity<ResponseUtil> getAllSymptoms(@PathVariable int patientId){
        try {
            List<SymptomDTO> symptomDTOS = symptomService.getSymptomsByPatientId(patientId);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseUtil(200,"Symptoms Fetched Successfully",symptomDTOS));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseUtil(500,e.getMessage(),null));
        }
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
    @PutMapping("/update")
    public ResponseEntity<ResponseUtil> updateSymptom(@RequestBody SymptomDTO symptomDTO){
        try {
            symptomService.update(symptomDTO);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseUtil(200,"Symptom Updated Successfully",null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseUtil(500,e.getMessage(),null));
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseUtil> deleteSymptom(@PathVariable int id){
        try {
            symptomService.delete(id);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseUtil(200,"Symptom Deleted Successfully",null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseUtil(500,e.getMessage(),null));
        }
    }

}
