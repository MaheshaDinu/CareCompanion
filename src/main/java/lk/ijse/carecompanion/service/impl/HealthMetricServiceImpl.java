package lk.ijse.carecompanion.service.impl;

import lk.ijse.carecompanion.dto.HealthMetricDTO;
import lk.ijse.carecompanion.entity.HealthMetric;
import lk.ijse.carecompanion.entity.Patient;
import lk.ijse.carecompanion.repository.HealthMetricRepo;
import lk.ijse.carecompanion.repository.PatientRepo;
import lk.ijse.carecompanion.service.HealthMetricService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class HealthMetricServiceImpl implements HealthMetricService {
    @Autowired
    private HealthMetricRepo healthMetricRepo;
    @Autowired
    private PatientRepo patientRepo;
    @Autowired
    private ModelMapper modelMapper;
    public void addHealthMetrics(HealthMetricDTO healthMetricDTO) {
        Optional<Patient> optPatient = patientRepo.findById(healthMetricDTO.getPatientId());
        if (!optPatient.isPresent()) {
            throw new RuntimeException("Patient not found with id: " + healthMetricDTO.getPatientId());
        }
        Patient patient = optPatient.get();

        HealthMetric healthMetric = modelMapper.map(healthMetricDTO, HealthMetric.class);
        healthMetric.setPatient(patient);
        healthMetricRepo.save(healthMetric);
    }
}
