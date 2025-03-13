package lk.ijse.carecompanion.service;

import lk.ijse.carecompanion.dto.AppointmentDTO;

import java.util.List;

public interface AppointmentService {
    void save(AppointmentDTO appointmentDTO);
    void update(AppointmentDTO appointmentDTO);
    void delete(int id);
    public List<AppointmentDTO> getAppointmentsByPatientId(int patientId);
}
