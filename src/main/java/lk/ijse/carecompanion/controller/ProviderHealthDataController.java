package lk.ijse.carecompanion.controller;

import jakarta.servlet.http.HttpServletRequest;
import lk.ijse.carecompanion.dto.HealthMetricChartDTO;
import lk.ijse.carecompanion.dto.ProviderDTO;
import lk.ijse.carecompanion.enums.HealthMetricType;
import lk.ijse.carecompanion.service.HealthMetricService;
import lk.ijse.carecompanion.service.JWTService;
import lk.ijse.carecompanion.service.PatientThresholdService;
import lk.ijse.carecompanion.service.ProviderService;
import lk.ijse.carecompanion.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/provider/health-data")
public class ProviderHealthDataController {
    @Autowired
    ProviderService providerService;
    @Autowired
    JWTService jwtService;
    @Autowired
    HealthMetricService healthMetricService;
    @Autowired
    PatientThresholdService patientThresholdService;

    @GetMapping("/getProvider")
    public ResponseEntity<ResponseUtil> getProvider(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ResponseUtil(401, "Unauthorized", null));
        }

        String token = authHeader.substring(7);
        String username = jwtService.extractUsername(token);

        ProviderDTO providerDTO = providerService.getByUserName(username);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseUtil(200, "success", providerDTO));
    }
    @GetMapping("/getPatients")
    public ResponseEntity<ResponseUtil> getPatients(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ResponseUtil(401, "Unauthorized", null));
        }

        String token = authHeader.substring(7);
        String username = jwtService.extractUsername(token);

        ProviderDTO providerDTO = providerService.getByUserName(username);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseUtil(200, "success", providerDTO.getPatients()));
    }
    @GetMapping("/getPatientHealthMetrics/{id}")
    public ResponseEntity<ResponseUtil> getPatientHealthMetrics(@PathVariable int id,@RequestParam(value = "startDate", required = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                                                @RequestParam(value = "endDate", required = false)
                                                                    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
                                                                @RequestParam(value = "sort", defaultValue = "timestamp,desc") String sort ) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseUtil(200, "success", healthMetricService.getPatientHealthMetrics(id, startDate, endDate, sort)));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseUtil(500,e.getMessage(),null));
        }
    }
    @GetMapping("/getPatientThresholds/{id}")
    public ResponseEntity<ResponseUtil> getPatientThresholds(@PathVariable int id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseUtil(200, "success", patientThresholdService.getPatientThresholdsByPatientId(id)));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseUtil(500,e.getMessage(),null));
        }
    }
    @GetMapping("/getHealthMetricChartData/{patientId}")
    public ResponseEntity<ResponseUtil> getMetricChart(
            @PathVariable int patientId,
            @RequestParam HealthMetricType metricType,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate endDate,
            HttpServletRequest request
    ) {

        HealthMetricChartDTO chart = healthMetricService.getChartData(
                patientId, metricType, startDate, endDate
        );
        return ResponseEntity.ok(
                new ResponseUtil(200, "Chart data fetched", chart)
        );
    }
}
