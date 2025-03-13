package lk.ijse.carecompanion.service;

import lk.ijse.carecompanion.dto.SymptomDTO;

import java.util.List;

public interface SymptomService {
    void save(SymptomDTO symptomDTO);
    void update(SymptomDTO symptomDTO);
    void delete(int id);
    List<SymptomDTO> getSymptomsByPatientId(int patientId);
}
