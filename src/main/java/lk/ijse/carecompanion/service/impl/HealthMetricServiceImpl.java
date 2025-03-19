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
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class HealthMetricServiceImpl implements HealthMetricService {
    @Autowired
    private HealthMetricRepo healthMetricRepo;
    @Autowired
    private PatientRepo patientRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Transactional
    @Override
    public void save(HealthMetricDTO healthMetricDTO) {
        Optional<Patient> optPatient = patientRepo.findById(healthMetricDTO.getPatientId());
        if (!optPatient.isPresent()) {
            throw new RuntimeException("Patient not found with id: " + healthMetricDTO.getPatientId());
        }
        Patient patient = optPatient.get();

        HealthMetric healthMetric = modelMapper.map(healthMetricDTO, HealthMetric.class);
        healthMetric.setPatient(patient);
        healthMetricRepo.save(healthMetric);
    }
    @Transactional
    @Override
    public void update(HealthMetricDTO healthMetricDTO) {
        HealthMetric healthMetric = modelMapper.map(healthMetricDTO, HealthMetric.class);
        healthMetricRepo.save(healthMetric);
    }
    @Transactional
    @Override
    public void delete(int id) {
        healthMetricRepo.deleteById(id);
    }
    public List<HealthMetricDTO> getHealthMetricsByPatientId(int patientId) {
        List<HealthMetric> healthMetrics = healthMetricRepo.findByPatientId(patientId);
        return healthMetrics.stream().map(healthMetric -> modelMapper.map(healthMetric, HealthMetricDTO.class)).toList();
    }
}
