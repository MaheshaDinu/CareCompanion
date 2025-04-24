package lk.ijse.carecompanion.controller;

import jakarta.servlet.http.HttpServletRequest;
import lk.ijse.carecompanion.dto.ProviderDTO;
import lk.ijse.carecompanion.service.JWTService;
import lk.ijse.carecompanion.service.PatientService;
import lk.ijse.carecompanion.service.ProviderService;
import lk.ijse.carecompanion.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/provider/patients")
public class ProviderPatientsController {
    @Autowired
    ProviderService providerService;
    @Autowired
    JWTService jwtService;
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
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseUtil(200, "success", providerDTO.getPatients()));
    }
    @GetMapping("/getPatient/{patientId}")
    public ResponseEntity<ResponseUtil> getPatient(@PathVariable int patientId) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseUtil(200, "success", patientService.getPatientById(patientId)));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseUtil(500, e.getMessage(), null));
        }
    }

}
