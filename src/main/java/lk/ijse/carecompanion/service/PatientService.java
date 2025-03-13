package lk.ijse.carecompanion.service;

import lk.ijse.carecompanion.dto.PatientDTO;
import lk.ijse.carecompanion.dto.PatientRegistrationDTO;

import java.util.List;

public interface PatientService {
    public void register(PatientRegistrationDTO patientRegistrationDTO);
    public  void update(PatientRegistrationDTO patientRegistrationDTO);
    public  void delete(int id);
    List<PatientDTO> getAll();
    PatientDTO getByUserName(String userName);
}
