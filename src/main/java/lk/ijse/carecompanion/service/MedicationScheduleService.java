package lk.ijse.carecompanion.service;

import lk.ijse.carecompanion.dto.MedicationScheduleDTO;

public interface MedicationScheduleService {
    void save(MedicationScheduleDTO medicationScheduleDTO);
    void update(MedicationScheduleDTO medicationScheduleDTO);
    void delete(int id);
}
