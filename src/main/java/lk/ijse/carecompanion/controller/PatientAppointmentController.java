package lk.ijse.carecompanion.controller;

import lk.ijse.carecompanion.dto.AppointmentDTO;
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
@RequestMapping("/patient/appointment")
public class PatientAppointmentController {
    @Autowired
    AppointmentService appointmentService;
    @Autowired
    JWTService jwtService;
    @Autowired
    ProviderService providerService;

    @GetMapping("/getPatientAppointments/{id}")
    public ResponseEntity<ResponseUtil> getPatientAppointments(@PathVariable int id){
        try {
            List<AppointmentDTO> appointmentDTOS = appointmentService.getAppointmentsByPatientId(id);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseUtil(200,"success",appointmentDTOS));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseUtil(500,e.getMessage(),null));
        }
    }
    @GetMapping("/getProvider/{id}")
    public ResponseEntity<ResponseUtil> getProvider(@PathVariable int id) {return ResponseEntity.status(HttpStatus.OK).body(new ResponseUtil(200,"success",providerService.getById(id)));}
    @PostMapping ("RequestAppointment")
    public ResponseEntity<ResponseUtil> requestAppointment(@RequestBody AppointmentDTO appointmentDTO){
        try {
            appointmentService.save(appointmentDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseUtil(201,"success",null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseUtil(500,e.getMessage(),null));
        }
    }

}
