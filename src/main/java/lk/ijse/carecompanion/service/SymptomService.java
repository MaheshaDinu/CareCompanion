package lk.ijse.carecompanion.service;

import lk.ijse.carecompanion.dto.SymptomDTO;

public interface SymptomService {
    void save(SymptomDTO symptomDTO);
    void update(SymptomDTO symptomDTO);
    void delete(int id);
}
