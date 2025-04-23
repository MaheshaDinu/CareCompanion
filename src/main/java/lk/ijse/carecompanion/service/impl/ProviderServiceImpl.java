package lk.ijse.carecompanion.service.impl;

import lk.ijse.carecompanion.dto.*;
import lk.ijse.carecompanion.entity.*;
import lk.ijse.carecompanion.enums.AlertStatus;
import lk.ijse.carecompanion.enums.AppointmentStatus;
import lk.ijse.carecompanion.enums.HealthMetricType;
import lk.ijse.carecompanion.enums.Severity;
import lk.ijse.carecompanion.repository.*;
import lk.ijse.carecompanion.service.JWTService;
import lk.ijse.carecompanion.service.ProviderService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ProviderServiceImpl implements ProviderService {
    @Autowired
    ProviderRepo providerRepo;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JWTService jwtService;
    @Autowired
    AppointmentRepo appointmentRepo;
    @Autowired
    AlertRepo alertRepo;
    @Autowired
    PatientRepo patientRepo;
    @Autowired
    PatientThresholdRepo patientThresholdRepo;
    @Autowired
    HealthMetricRepo healthMetricRepo;
    AuthTokenDTO authTokenDTO = new AuthTokenDTO();
    @Transactional
    @Override
    public void register(ProviderRegistrationDTO providerRegistrationDTO){
        providerRegistrationDTO.setPassword(bCryptPasswordEncoder.encode(providerRegistrationDTO.getPassword()));
        Provider provider = modelMapper.map(providerRegistrationDTO,Provider.class);
        providerRepo.save(provider);
    }
    @Transactional
    @Override
    public void update(ProviderRegistrationDTO providerDTO){
        Provider provider = modelMapper.map(providerDTO,Provider.class);
        providerRepo.save(provider);
    }
    @Transactional
    @Override
    public void delete(int id){
        providerRepo.deleteById(id);
    }
    @Transactional
    @Override
    public List<ProviderDTO> getAll(){
        return modelMapper.map(providerRepo.findAll(),new TypeToken<List<ProviderDTO>>(){}.getType());
    }
    public ProviderDTO getByUserName(String userName){
        return modelMapper.map(providerRepo.findByUserName(userName),ProviderDTO.class);
    }
    @Transactional
    @Override
    public AuthTokenDTO verifyProvider(UserLoginDTO userDTO) {
        Optional<Provider> optProvider = providerRepo.findByUserName(userDTO.getUsername());
        if (optProvider.isPresent()) {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userDTO.getUsername(), userDTO.getPassword()));
            if (authentication.isAuthenticated()) {
                authTokenDTO.setAuthenticated(true);
                authTokenDTO.setToken(jwtService.generateToken(userDTO.getUsername(),userDTO.getRole()));
                authTokenDTO.setMessage("success");
                return authTokenDTO;
            }
            authTokenDTO.setAuthenticated(false);
            authTokenDTO.setMessage("fail");
            return authTokenDTO;
        }
        authTokenDTO.setAuthenticated(false);
        authTokenDTO.setMessage("Provider not found");
        return authTokenDTO;
    }

    @Override
    public List<PatientDTO> getPatientsByProviderId(int providerId) {
        List<Patient> patients = providerRepo.findById(providerId).get().getPatients();
        return patients.stream()
                .map(patient -> modelMapper.map(patient, PatientDTO.class))
                .toList();
    }
    @Override
    public ProviderDTO getById(int id) {
        return modelMapper.map(providerRepo.findById(id), ProviderDTO.class);
    }

    @Override
    public DashboardSummaryDTO getDashboardSummary(int providerId) {
        DashboardSummaryDTO summaryDTO = new DashboardSummaryDTO();
        // Calculate dates
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startOfToday = now.toLocalDate().atStartOfDay();
        LocalDateTime endOfToday = now.toLocalDate().atTime(23, 59, 59);
        LocalDateTime monthStart = now.toLocalDate().withDayOfMonth(1).atStartOfDay();

        //Appointments
        summaryDTO.setUpcomingAppointmentsCount(appointmentRepo.countByProviderIdAndStatusAndDateTimeAfter(providerId, AppointmentStatus.CONFIRMED, now));
        summaryDTO.setTodayAppointmentsCount(
                appointmentRepo.countByProviderIdAndDateTimeBetween(
                        providerId,
                        startOfToday,
                        endOfToday
                )
        );

        //patients
        summaryDTO.setPatientCount(patientRepo.countByProviderId(providerId));
        summaryDTO.setNewPatientsCount(
                patientRepo.countByProviderIdAndCreatedAtAfter(
                        providerId,
                        monthStart
                )
        );
        // Critical cases (patients with metrics exceeding thresholds)
        summaryDTO.setCriticalCasesCount(
                countCriticalCases(providerId)
        );

        return summaryDTO;


    }

    @Override
    public List<AppointmentDTO> getUpcomingAppointments(int id, int limit) {
        int validLimit = Math.max(limit, 1);  // Ensure at least 1 result

        // Get current time
        LocalDateTime now = LocalDateTime.now();

        // Fetch appointments with pagination and sorting
        Pageable pageable = PageRequest.of(0, validLimit, Sort.by("dateTime").ascending());

        List<Appointment> appointments = appointmentRepo.findConfirmedUpcomingAppointments(
                id,
                AppointmentStatus.CONFIRMED,
                now,
                pageable
        );
        return appointments.stream().map(appointment -> modelMapper.map(appointment, AppointmentDTO.class))
                .toList();
    }

    private int countCriticalCases(int providerId) {
        List<Patient> patients = patientRepo.findByProviderId(providerId);
        int criticalCount = 0;

        for (Patient patient : patients) {
            List<PatientThreshold> thresholds = patientThresholdRepo.findByPatientId(patient.getId());

            for (PatientThreshold threshold : thresholds) {
                HealthMetric latestMetric = healthMetricRepo.findTopByPatientIdAndTypeOrderByTimestampDesc(
                        patient.getId(),
                        threshold.getType()
                );

                if (latestMetric != null &&
                        (latestMetric.getValue() < threshold.getMinValue() ||
                                latestMetric.getValue() > threshold.getMaxValue())) {
                    criticalCount++;
                    break; // Count patient once if any threshold is breached
                }
            }
        }

        return criticalCount;
    }

}
