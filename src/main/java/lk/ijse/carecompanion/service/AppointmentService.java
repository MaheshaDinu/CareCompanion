package lk.ijse.carecompanion.service;

import lk.ijse.carecompanion.dto.AppointmentDTO;

public interface AppointmentService {
    void save(AppointmentDTO appointmentDTO);
    void update(AppointmentDTO appointmentDTO);
}
