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
        // Validate patient exists
        Patient patient = patientRepo.findById(healthMetricDTO.getPatientId())
                .orElseThrow(() -> new RuntimeException(
                        "Patient not found with id: " + healthMetricDTO.getPatientId()
                ));

        // Validate metric data based on type
        validateMetric(healthMetricDTO);

        HealthMetric healthMetric = modelMapper.map(healthMetricDTO, HealthMetric.class);
        healthMetric.setPatient(patient);
        healthMetricRepo.save(healthMetric);
    }

    private void validateMetric(HealthMetricDTO dto) {
        if (dto.getType() == null) {
            throw new IllegalArgumentException("Metric type is required");
        }

        switch (dto.getType()) {
            case BLOOD_PRESSURE:
                if (dto.getSystolic() == null || dto.getDiastolic() == null) {
                    throw new IllegalArgumentException(
                            "Both systolic and diastolic values are required for blood pressure"
                    );
                }
                // Explicitly set value to null for blood pressure
                dto.setValue(null);
                break;
            default:
                if (dto.getValue() == null) {
                    throw new IllegalArgumentException(
                            "Value is required for " + dto.getType()
                    );
                }
                // Clear blood pressure fields for non-BP types
                dto.setSystolic(null);
                dto.setDiastolic(null);
                break;
        }
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
    @Transactional
    @Override
    public List<HealthMetricDTO> getHealthMetricsByPatientId(int patientId) {
        List<HealthMetric> healthMetrics = healthMetricRepo.findByPatientId(patientId);
        return healthMetrics.stream().map(healthMetric -> modelMapper.map(healthMetric, HealthMetricDTO.class)).toList();
    }
    @Transactional
    @Override
    public HealthMetricDTO getHealthMetricById(int id) {
        Optional<HealthMetric> optionalHealthMetric = healthMetricRepo.findById(id);
        return optionalHealthMetric.map(healthMetric -> modelMapper.map(healthMetric, HealthMetricDTO.class)).orElse(null);
    }
}
