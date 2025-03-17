package lk.ijse.carecompanion.service;

import lk.ijse.carecompanion.dto.*;

import java.util.List;

public interface PatientService {
    void register(PatientRegistrationDTO patientRegistrationDTO);
    void update(PatientRegistrationDTO patientRegistrationDTO);
    void delete(int id);
    List<PatientDTO> getAll();
    PatientDTO getByUserName(String userName);

    AuthTokenDTO verifyPatient(UserLoginDTO userDTO);
}
