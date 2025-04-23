package lk.ijse.carecompanion.service;

import lk.ijse.carecompanion.dto.*;
import lk.ijse.carecompanion.enums.HealthMetricType;

import java.util.List;

public interface PatientService {
    void register(PatientRegistrationDTO patientRegistrationDTO);
    void update(PatientRegistrationDTO patientRegistrationDTO);
    void delete(int id);
    List<PatientDTO> getAll();
    PatientDTO getByUserName(String userName);

    AuthTokenDTO verifyPatient(UserLoginDTO userDTO);
    List<HealthMetricDTO> getHealthMetricsByPatientId(int patientId, HealthMetricType type);

    PatientDTO getPatientById(int id);
}
