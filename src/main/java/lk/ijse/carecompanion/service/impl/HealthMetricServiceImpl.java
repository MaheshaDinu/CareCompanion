package lk.ijse.carecompanion.service.impl;

import lk.ijse.carecompanion.dto.HealthMetricChartDTO;
import lk.ijse.carecompanion.dto.HealthMetricDTO;
import lk.ijse.carecompanion.entity.HealthMetric;
import lk.ijse.carecompanion.entity.Patient;
import lk.ijse.carecompanion.enums.HealthMetricType;
import lk.ijse.carecompanion.repository.HealthMetricRepo;
import lk.ijse.carecompanion.repository.PatientRepo;
import lk.ijse.carecompanion.service.HealthMetricService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @Override
    public List<HealthMetricDTO> getPatientHealthMetrics(
            int patientId,
            LocalDate startDate,
            LocalDate endDate,
            String sortParam
    ) {
        // 1. Parse sortParam, default to timestamp desc if missing
        String[] parts = (sortParam != null ? sortParam : "timestamp,desc").split(",");
        Sort sort = Sort.by(
                Sort.Direction.fromString(parts[1].trim()),
                parts[0].trim()
        );

        // 2. Build date‐time range
        LocalDateTime start = startDate != null
                ? startDate.atStartOfDay()
                : LocalDateTime.MIN;
        LocalDateTime end = endDate != null
                ? endDate.atTime(LocalTime.MAX)
                : LocalDateTime.MAX;

        // 3. Query repository
        List<HealthMetric> metrics = healthMetricRepo
                .findByPatient_IdAndTimestampBetween(patientId, start, end, sort);
        System.out.println(metrics);

        // 4. Map entities to DTOs
        Type listType = new TypeToken<List<HealthMetricDTO>>(){}.getType();
        return modelMapper.map(metrics, listType);
    }

    @Override
    public HealthMetricChartDTO getChartData(int patientId,
                                                   HealthMetricType metricType,
                                                   LocalDate startDate,
                                                   LocalDate endDate) {
        // Build start/end datetimes
        LocalDateTime start = startDate != null
                ? startDate.atStartOfDay()
                : LocalDateTime.MIN;
        LocalDateTime end = endDate != null
                ? endDate.atTime(LocalTime.MAX)
                : LocalDateTime.MAX;

        // Fetch sorted by timestamp asc
        Sort sort = Sort.by("timestamp").ascending();
        List<HealthMetric> list = healthMetricRepo.findByPatient_IdAndTypeAndTimestampBetween(
                patientId, metricType, start, end, sort
        );

        // Labels are formatted dates
        List<String> labels = list.stream()
                .map(m -> m.getTimestamp().toLocalDate().toString())
                .collect(Collectors.toList());

        // For blood pressure we need two series
        if (metricType == HealthMetricType.BLOOD_PRESSURE) {
            List<Double> systolic = list.stream()
                    .map(m -> (double) m.getSystolic())
                    .collect(Collectors.toList());
            List<Double> diastolic = list.stream()
                    .map(m -> (double) m.getDiastolic())
                    .collect(Collectors.toList());

            return new HealthMetricChartDTO(labels, systolic, diastolic);
        }

        // For other metrics, you might want to return via a different DTO,
        // but here we’ll just put everything into the systolic list:
        List<Double> values = list.stream()
                .map(HealthMetric::getValue)
                .collect(Collectors.toList());
        return new HealthMetricChartDTO(labels, values, List.of());
    }


}
