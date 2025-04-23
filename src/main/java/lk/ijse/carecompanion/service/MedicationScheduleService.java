package lk.ijse.carecompanion.service;

import lk.ijse.carecompanion.dto.MedicationScheduleDTO;

import java.util.List;

public interface MedicationScheduleService {
    void save(MedicationScheduleDTO medicationScheduleDTO);
    void update(MedicationScheduleDTO medicationScheduleDTO);
    void delete(int id);
    List<MedicationScheduleDTO> getMedicationSchedulesByPatientId(int patientId);

    MedicationScheduleDTO getMedicationScheduleById(int id);
}
