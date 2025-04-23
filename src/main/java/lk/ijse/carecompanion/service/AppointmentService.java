package lk.ijse.carecompanion.service;

import lk.ijse.carecompanion.dto.AppointmentDTO;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.OffsetDateTime;
import java.util.List;

public interface AppointmentService {
    void save(AppointmentDTO appointmentDTO);
    void update(AppointmentDTO appointmentDTO);
    void delete(int id);
    List<AppointmentDTO> getAppointmentsByPatientId(int patientId);
    List<AppointmentDTO> getAppointmentsByProviderId(int providerId);

    List<AppointmentDTO> getAppointmentsByProviderAndFilters(int id, @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    OffsetDateTime start, @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    OffsetDateTime end, String status, String date, Boolean future);

    AppointmentDTO getAppointmentById(int id);
}
