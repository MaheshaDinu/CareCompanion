package lk.ijse.carecompanion.service.impl;

import jakarta.persistence.criteria.Predicate;
import lk.ijse.carecompanion.dto.AppointmentDTO;
import lk.ijse.carecompanion.entity.Appointment;
import lk.ijse.carecompanion.entity.Patient;
import lk.ijse.carecompanion.entity.Provider;
import lk.ijse.carecompanion.enums.AppointmentStatus;
import lk.ijse.carecompanion.repository.AppointmentRepo;
import lk.ijse.carecompanion.repository.PatientRepo;
import lk.ijse.carecompanion.repository.ProviderRepo;
import lk.ijse.carecompanion.service.AppointmentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AppointmentServiceImpl implements AppointmentService {
    @Autowired
    private AppointmentRepo appointmentRepo;
    @Autowired
    private PatientRepo patientRepo;
    @Autowired
    private ProviderRepo providerRepo;
    @Autowired
    private ModelMapper modelMapper;
    public void save(AppointmentDTO appointmentDTO){
        Appointment appointment = modelMapper.map(appointmentDTO, Appointment.class);
        Optional<Patient> optPatient = patientRepo.findById(appointmentDTO.getPatientId());
        Optional<Provider> optProvider =providerRepo.findById(appointmentDTO.getProviderId());
        if (optPatient.isEmpty() && optProvider.isEmpty()) {
            throw new RuntimeException("Patient not found with id: " + appointmentDTO.getPatientId()+"/n"+ "Provider not found with id: " + appointmentDTO.getProviderId());

        }
            Patient patient = optPatient.get();
            Provider provider = optProvider.get();
            appointment.setPatient(patient);
            appointment.setProvider(provider);
            appointmentRepo.save(appointment);
    }
    public void update(AppointmentDTO appointmentDTO){
        Appointment appointment = modelMapper.map(appointmentDTO, Appointment.class);
        appointmentRepo.save(appointment);
    }
    public void delete(int id){
        appointmentRepo.deleteById(id);
    }
    public List<AppointmentDTO> getAppointmentsByPatientId(int patientId) {
        List<Appointment> appointments = appointmentRepo.findByPatientId(patientId);
        return appointments.stream()
                .map(appointment -> modelMapper.map(appointment, AppointmentDTO.class))
                .toList();
    }
    public List<AppointmentDTO> getAppointmentsByProviderId(int providerId) {
        List<Appointment> appointments = appointmentRepo.findByProviderId(providerId);
        return appointments.stream()
                .map(appointment -> modelMapper.map(appointment, AppointmentDTO.class))
                .toList();
    }

    @Override
    public List<AppointmentDTO> getAppointmentsByProviderAndFilters(int id, String start, String end, String status, String date, Boolean future) {
        Specification<Appointment> spec = (root, query, cb) -> {
            List<jakarta.persistence.criteria.Predicate> predicates = new ArrayList<>();

            // Provider filter
            predicates.add(cb.equal(root.get("provider").get("id"), id));

            // Date range filters
            if (start != null) {
                LocalDateTime startDt = LocalDate.parse(start).atStartOfDay();
                predicates.add(cb.greaterThanOrEqualTo(root.get("dateTime"), startDt));
            }
            if (end != null) {
                LocalDateTime endDt = LocalDate.parse(end).atTime(23, 59, 59);
                predicates.add(cb.lessThanOrEqualTo(root.get("dateTime"), endDt));
            }

            // Exact date filter
            if (date != null) {
                LocalDateTime dayStart = LocalDate.parse(date).atStartOfDay();
                LocalDateTime dayEnd   = dayStart.plusDays(1).minusSeconds(1);
                predicates.add(cb.between(root.get("dateTime"), dayStart, dayEnd));
            }

            // Status filter
            if (status != null) {
                AppointmentStatus st = AppointmentStatus.valueOf(status);
                predicates.add(cb.equal(root.get("status"), st));
            }

            // Future appointments only
            if (future != null && future) {
                predicates.add(cb.greaterThan(root.get("dateTime"), LocalDateTime.now()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // Execute query and map to DTO
        List<Appointment> results = appointmentRepo.findAll(spec);
        return results.stream()
                .map(app -> modelMapper.map(app, AppointmentDTO.class))
                .toList();
    }
}
