package lk.ijse.carecompanion.service;

import lk.ijse.carecompanion.dto.AppointmentDTO;

import java.util.List;

public interface AppointmentService {
    void save(AppointmentDTO appointmentDTO);
    void update(AppointmentDTO appointmentDTO);
    void delete(int id);
    List<AppointmentDTO> getAppointmentsByPatientId(int patientId);
    List<AppointmentDTO> getAppointmentsByProviderId(int providerId);

    List<AppointmentDTO> getAppointmentsByProviderAndFilters(int id, String start, String end, String status, String date, Boolean future);
}
