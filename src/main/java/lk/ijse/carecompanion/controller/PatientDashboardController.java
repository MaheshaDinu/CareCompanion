package lk.ijse.carecompanion.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lk.ijse.carecompanion.dto.PatientDTO;
import lk.ijse.carecompanion.enums.HealthMetricType;
import lk.ijse.carecompanion.service.JWTService;
import lk.ijse.carecompanion.service.PatientService;
import lk.ijse.carecompanion.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/patient/dashboard")
public class PatientDashboardController {
    @Autowired
    JWTService jwtService;
    @Autowired
    PatientService patientService;
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
    @GetMapping("/getPatientHealthMetric")
    public ResponseEntity<ResponseUtil> getAHealthMetric( @RequestParam int patientId, @RequestParam String stringHealthMetricType){
        HealthMetricType heathMetricType = HealthMetricType.valueOf(stringHealthMetricType);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseUtil(200,"",patientService.getHealthMetricsByPatientId(patientId,heathMetricType)));
    }
}
