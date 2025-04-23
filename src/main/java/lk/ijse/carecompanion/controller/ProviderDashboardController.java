package lk.ijse.carecompanion.controller;

import jakarta.servlet.http.HttpServletRequest;
import lk.ijse.carecompanion.dto.AppointmentDTO;
import lk.ijse.carecompanion.dto.DashboardSummaryDTO;
import lk.ijse.carecompanion.dto.ProviderDTO;
import lk.ijse.carecompanion.service.JWTService;
import lk.ijse.carecompanion.service.PatientService;
import lk.ijse.carecompanion.service.ProviderService;
import lk.ijse.carecompanion.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/provider/dashboard")
public class ProviderDashboardController {
    @Autowired
    ProviderService providerService;
    @Autowired
    PatientService patientService;
    @Autowired
    JWTService jwtService;

    @GetMapping("/getProvider")
    public ResponseEntity<ResponseUtil> getProvider(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ResponseUtil(401,"Unauthorized",null));
        }

        String token = authHeader.substring(7);
        String username = jwtService.extractUsername(token);

        ProviderDTO providerDTO = providerService.getByUserName(username);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseUtil(200,"success",providerDTO));
    }

    @GetMapping("/summary")
    public ResponseEntity<ResponseUtil> getSummary(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ResponseUtil(401,"Unauthorized",null));
        }

        String token = authHeader.substring(7);
        String username = jwtService.extractUsername(token);

        ProviderDTO providerDTO = providerService.getByUserName(username);
        try {
            DashboardSummaryDTO summaryDTO = providerService.getDashboardSummary(providerDTO.getId());
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseUtil(200,"success",summaryDTO));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseUtil(500, e.getMessage(), null));
        }


    }
    @GetMapping("/upComingAppointments")
    public ResponseEntity<ResponseUtil> upComingAppointments(HttpServletRequest request,@RequestParam(defaultValue = "4") int limit){
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ResponseUtil(401,"Unauthorized",null));
        }

        String token = authHeader.substring(7);
        String username = jwtService.extractUsername(token);

        ProviderDTO providerDTO = providerService.getByUserName(username);
        try {
            List<AppointmentDTO> appointments = providerService.getUpcomingAppointments(
                    providerDTO.getId(),
                    limit
            );
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseUtil(200,"success",appointments));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseUtil(500, e.getMessage(), null));
        }
    }
}
