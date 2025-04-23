package lk.ijse.carecompanion.controller;

import jakarta.servlet.http.HttpServletRequest;
import lk.ijse.carecompanion.dto.MedicationScheduleDTO;
import lk.ijse.carecompanion.dto.PatientDTO;
import lk.ijse.carecompanion.dto.ProviderDTO;
import lk.ijse.carecompanion.service.JWTService;
import lk.ijse.carecompanion.service.MedicationScheduleService;
import lk.ijse.carecompanion.service.PatientService;
import lk.ijse.carecompanion.service.ProviderService;
import lk.ijse.carecompanion.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/provider/medication-schedule")
public class ProviderMedicationScheduleController {
    @Autowired
    ProviderService providerService;
    @Autowired
    JWTService jwtService;
    @Autowired
    MedicationScheduleService medicationScheduleService;
    @Autowired
    PatientService patientService;

    @GetMapping("/getProvider")
    public ResponseEntity<ResponseUtil> getProvider(HttpServletRequest request){
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ResponseUtil(401,"Unauthorized",null));
        }

        String token = authHeader.substring(7);
        String username = jwtService.extractUsername(token);

        ProviderDTO providerDTO = providerService.getByUserName(username);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseUtil(200,"success",providerDTO));
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
        List<PatientDTO> patientDTOS = providerDTO.getPatients();
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseUtil(200, "success", patientDTOS));
    }
    @GetMapping("getMedicationSchedulesByPatient/{id}")
    public ResponseEntity<ResponseUtil> getMedicationSchedulesByPatient(@PathVariable int id) {
        try {
            List<MedicationScheduleDTO> medicationScheduleDTOS = medicationScheduleService.getMedicationSchedulesByPatientId(id);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseUtil(200, "success", medicationScheduleDTOS));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseUtil(500, e.getMessage(), null));
        }

    }
    @GetMapping("getMedicationSchedule/{id}")
    public ResponseEntity<ResponseUtil> getMedicationSchedule(@PathVariable int id) {
        try {
            MedicationScheduleDTO medicationScheduleDTO = medicationScheduleService.getMedicationScheduleById(id);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseUtil(200, "success", medicationScheduleDTO));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseUtil(500, e.getMessage(), null));
        }
    }
    @PostMapping("addMedicationSchedule")
    public ResponseEntity<ResponseUtil> addMedicationSchedule(@RequestBody MedicationScheduleDTO medicationScheduleDTO) {
        try {
            medicationScheduleService.save(medicationScheduleDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseUtil(201, "success", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseUtil(500, e.getMessage(), null));
        }
    }
    @PutMapping("updateMedicationSchedule/{id}")
    public ResponseEntity<ResponseUtil> updateMedicationSchedule(@PathVariable int id, @RequestBody MedicationScheduleDTO medicationScheduleDTO) {
        try {
            medicationScheduleService.update(medicationScheduleDTO);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseUtil(200, "success", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseUtil(500, e.getMessage(), null));
        }
    }
}
