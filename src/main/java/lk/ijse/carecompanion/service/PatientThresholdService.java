package lk.ijse.carecompanion.service;


import lk.ijse.carecompanion.dto.PatientThresholdDTO;

import java.util.List;

public interface PatientThresholdService {
    void save(PatientThresholdDTO patientThresholdDTO);

    void update(PatientThresholdDTO patientThresholdDTO);
    void delete(int id);
    List<PatientThresholdDTO> getPatientThresholdsByPatientId(int patientId);

}
