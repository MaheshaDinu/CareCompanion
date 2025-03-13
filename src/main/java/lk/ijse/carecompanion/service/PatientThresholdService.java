package lk.ijse.carecompanion.service;


import lk.ijse.carecompanion.dto.PatientThresholdDTO;

public interface PatientThresholdService {
    void save(PatientThresholdDTO patientThresholdDTO);

    void update(PatientThresholdDTO patientThresholdDTO);
    void delete(int id);

}
