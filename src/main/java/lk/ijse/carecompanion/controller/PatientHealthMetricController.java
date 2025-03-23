package lk.ijse.carecompanion.controller;

import jakarta.servlet.http.HttpServletRequest;
import lk.ijse.carecompanion.dto.HealthMetricDTO;
import lk.ijse.carecompanion.dto.PatientDTO;
import lk.ijse.carecompanion.enums.HealthMetricType;
import lk.ijse.carecompanion.service.HealthMetricService;
import lk.ijse.carecompanion.service.JWTService;
import lk.ijse.carecompanion.service.PatientService;
import lk.ijse.carecompanion.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patient/health-metric")
public class PatientHealthMetricController {
    @Autowired
    PatientService patientService;
    @Autowired
    HealthMetricService healthMetricService;
    @Autowired
    JWTService jwtService;

    @GetMapping("/getPatientHealthMetric")
    public ResponseEntity<ResponseUtil> getAHealthMetric(@RequestParam int patientId, @RequestParam String stringHealthMetricType){
        HealthMetricType heathMetricType = HealthMetricType.valueOf(stringHealthMetricType);
        try {
            List<HealthMetricDTO> healthMetricDTOS = patientService.getHealthMetricsByPatientId(patientId,heathMetricType);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseUtil(200,"",healthMetricDTOS));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseUtil(500,e.getMessage(),null));
        }
    }
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
    @GetMapping("entry/{id}")
    public ResponseEntity<ResponseUtil> getHealthMetric(@PathVariable int id){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseUtil(200,"",healthMetricService.getHealthMetricById(id)));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseUtil(500,e.getMessage(),null));
        }
    }
    @PostMapping("/addHealthMetric")
    public ResponseEntity<ResponseUtil> addHealthMetric(@RequestBody HealthMetricDTO healthMetricDTO){
        try {
            healthMetricService.save(healthMetricDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseUtil(201,"Health Metric Added Successfully",null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseUtil(500,e.getMessage(),null));
        }
    }
    @DeleteMapping("/delete")
    public ResponseEntity<ResponseUtil> deleteHealthMetric(@RequestParam int id){
        try {
            healthMetricService.delete(id);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseUtil(200,"Health Metric Deleted Successfully",null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseUtil(500,e.getMessage(),null));
        }
    }
    @PutMapping("/update")
    public ResponseEntity<ResponseUtil> updateHealthMetric(@RequestBody HealthMetricDTO healthMetricDTO){
        try {
            healthMetricService.update(healthMetricDTO);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseUtil(200,"Health Metric Updated Successfully",null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseUtil(500,e.getMessage(),null));
        }
    }
}
