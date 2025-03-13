package lk.ijse.carecompanion.service.impl;

import lk.ijse.carecompanion.dto.PatientThresholdDTO;
import lk.ijse.carecompanion.entity.Patient;
import lk.ijse.carecompanion.entity.PatientThreshold;
import lk.ijse.carecompanion.repository.PatientRepo;
import lk.ijse.carecompanion.repository.PatientThresholdRepo;
import lk.ijse.carecompanion.service.PatientService;
import lk.ijse.carecompanion.service.PatientThresholdService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PatientThresholdServiceImpl implements PatientThresholdService {
    @Autowired
    PatientThresholdRepo patientThresholdRepo;
    @Autowired
    PatientRepo patientRepo;
    @Autowired
    private ModelMapper modelMapper;
    public void addPatientThreshold(PatientThresholdDTO patientThresholdDTO) {
        Optional<Patient> optPatient = patientRepo.findById(patientThresholdDTO.getPatientId());
        if (!optPatient.isPresent()) {
            throw new RuntimeException("Patient not found with id: " + patientThresholdDTO.getPatientId());
        }
        Patient patient = optPatient.get();

        PatientThreshold patientThreshold = modelMapper.map(patientThresholdDTO, PatientThreshold.class);
        patientThreshold.setPatient(patient);
        patientThresholdRepo.save(patientThreshold);
    }
}
