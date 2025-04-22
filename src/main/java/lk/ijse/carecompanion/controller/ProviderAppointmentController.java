package lk.ijse.carecompanion.controller;

import jakarta.servlet.http.HttpServletRequest;
import lk.ijse.carecompanion.dto.AppointmentDTO;
import lk.ijse.carecompanion.dto.ProviderDTO;
import lk.ijse.carecompanion.dto.ReScheduleReQuestDTO;
import lk.ijse.carecompanion.enums.AppointmentStatus;
import lk.ijse.carecompanion.service.AppointmentService;
import lk.ijse.carecompanion.service.JWTService;
import lk.ijse.carecompanion.service.ProviderService;
import lk.ijse.carecompanion.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/provider/appointment")
public class ProviderAppointmentController {
    @Autowired
    AppointmentService appointmentService;
    @Autowired
    JWTService jwtService;
    @Autowired
    ProviderService providerService;


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

    @GetMapping("/getAppointments")
    public ResponseEntity<ResponseUtil> getAppointments(
            HttpServletRequest request,
            @RequestParam(required = false) String start,
            @RequestParam(required = false) String end,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String date,
            @RequestParam(required = false) Boolean future) {

        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ResponseUtil(401, "Unauthorized", null));
        }

        String token = authHeader.substring(7);
        String username = jwtService.extractUsername(token);

        ProviderDTO provider = providerService.getByUserName(username);
        if (provider == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseUtil(404, "Provider not found", null));
        }

        List<AppointmentDTO> appointments = appointmentService.getAppointmentsByProviderAndFilters(
                provider.getId(),
                start,
                end,
                status,
                date,
                future
        );

        return ResponseEntity.ok(new ResponseUtil(200, "Success", appointments));
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

    @GetMapping("/getAppointment/{id}")
    public ResponseEntity<ResponseUtil> getAppointment(@RequestParam int id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseUtil(200, "", appointmentService.getAppointmentById(id)));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseUtil(500, e.getMessage(), null));
        }
    }

    @PostMapping("/createAppointment")
    public ResponseEntity<ResponseUtil> createAppointment(
            HttpServletRequest request,
            @RequestBody AppointmentDTO appointmentDTO) {

        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ResponseUtil(401, "Unauthorized", null));
        }

        String token = authHeader.substring(7);
        String username = jwtService.extractUsername(token);

        ProviderDTO provider = providerService.getByUserName(username);
        if (provider == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseUtil(404, "Provider not found", null));
        }

        appointmentDTO.setProviderId(provider.getId());

        if (appointmentDTO.getDateTime() == null ||
                appointmentDTO.getPurpose() == null ||
                appointmentDTO.getStatus() == null ||
                appointmentDTO.getPatientId() ==0) {
            return ResponseEntity.badRequest()
                    .body(new ResponseUtil(400, "Missing required fields", null));
        }
        try {
            appointmentService.save(appointmentDTO);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ResponseUtil(201, "Appointment created", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseUtil(500, e.getMessage(), null));
        }

    }
    @PatchMapping("/updateAppointment/{appointmentId}")
    public ResponseEntity<ResponseUtil> updateAppointment(
            HttpServletRequest request,
            @PathVariable int appointmentId,
            @RequestBody AppointmentStatus appointmentStatus) {

        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ResponseUtil(401, "Unauthorized", null));
        }

        String token = authHeader.substring(7);
        String username = jwtService.extractUsername(token);

        ProviderDTO provider = providerService.getByUserName(username);
        if (provider == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseUtil(404, "Provider not found", null));
        }

        // Verify appointment belongs to provider
        AppointmentDTO existingAppointment = appointmentService.getAppointmentById(
                appointmentId
        );

        if (existingAppointment == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseUtil(404, "Appointment not found", null));
        }

        // Update appointment
        try {
            if (existingAppointment.getProviderId()!=provider.getId()) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(new ResponseUtil(403, "Forbidden", null));
            }
            existingAppointment.setStatus(appointmentStatus);

            appointmentService.update(existingAppointment);

            return ResponseEntity.ok(new ResponseUtil(200, "Appointment updated", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseUtil(500, e.getMessage(), null));
        }

    }
    @PatchMapping("/reSchedule/{appointmentId}")
    public ResponseEntity<ResponseUtil> reSchedule(
            HttpServletRequest request,
            @PathVariable int appointmentId,
            @RequestBody ReScheduleReQuestDTO reScheduleReQuestDTO) {

        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ResponseUtil(401, "Unauthorized", null));
        }

        String token = authHeader.substring(7);
        String username = jwtService.extractUsername(token);

        ProviderDTO provider = providerService.getByUserName(username);
        if (provider == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseUtil(404, "Provider not found", null));
        }

        // Verify appointment belongs to provider
        AppointmentDTO existingAppointment = appointmentService.getAppointmentById(
                appointmentId
        );

        if (existingAppointment == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseUtil(404, "Appointment not found", null));
        }

        // Update appointment
        try {
            if (existingAppointment.getProviderId()!=provider.getId()) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(new ResponseUtil(403, "Forbidden", null));
            }
            existingAppointment.setDateTime(reScheduleReQuestDTO.getDateTime());
            appointmentService.update(existingAppointment);

            return ResponseEntity.ok(new ResponseUtil(200, "Appointment updated", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseUtil(500, e.getMessage(), null));
        }

    }

}
