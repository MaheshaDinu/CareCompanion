package lk.ijse.carecompanion.service.impl;

import lk.ijse.carecompanion.dto.*;
import lk.ijse.carecompanion.entity.*;
import lk.ijse.carecompanion.enums.HealthMetricType;
import lk.ijse.carecompanion.repository.*;
import lk.ijse.carecompanion.service.JWTService;
import lk.ijse.carecompanion.service.PatientService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PatientServiceImpl implements PatientService {
    @Autowired
    PatientRepo patientRepo;
    @Autowired
    ProviderRepo providerRepo;
    @Autowired
    HealthMetricRepo healthMetricRepo;
    @Autowired
    MedicationScheduleRepo medicationScheduleRepo;
    @Autowired
    SymptomRepo symptomRepo;
    @Autowired
    PatientThresholdRepo patientThresholdRepo;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JWTService jwtService;


    @Transactional
    @Override
    public void register(PatientRegistrationDTO patientRegistrationDTO){
        patientRegistrationDTO.setPassword(bCryptPasswordEncoder.encode(patientRegistrationDTO.getPassword()));
        Patient patient = modelMapper.map(patientRegistrationDTO, Patient.class);
        Optional<Provider> optProvider = providerRepo.findById(patientRegistrationDTO.getProviderId());
        if (optProvider.isEmpty()) {
            throw new RuntimeException("Provider not found with id: " + patientRegistrationDTO.getProviderId());
        }
        Provider provider = optProvider.get();
        patient.setProvider(provider);
        patientRepo.save(patient);

    }
    @Transactional
    public  void update(PatientRegistrationDTO patientRegistrationDTO){
        Patient patient = modelMapper.map(patientRegistrationDTO, Patient.class);
        patientRepo.save(patient);
    }
    @Transactional
    @Override
    public  void delete(int id){
        patientRepo.deleteById(id);
    }
    public PatientDTO getByUserName(String userName){
        return modelMapper.map(patientRepo.findByUserName(userName),PatientDTO.class);
    }
    @Transactional
    @Override
    public AuthTokenDTO verifyPatient(UserLoginDTO userDTO) {
        Optional<Patient> optPatient = patientRepo.findByUserName(userDTO.getUsername());
        AuthTokenDTO authTokenDTO = new AuthTokenDTO();
        if (optPatient.isPresent()) {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userDTO.getUsername(),userDTO.getPassword()));
            if (authentication.isAuthenticated()){

                authTokenDTO.setAuthenticated(true);
                authTokenDTO.setToken(jwtService.generateToken(userDTO.getUsername(),userDTO.getRole()));
                authTokenDTO.setMessage("Success");
                return authTokenDTO;
            }
            authTokenDTO.setAuthenticated(false);
            authTokenDTO.setMessage("Fail");
            return authTokenDTO;
        }
        authTokenDTO.setAuthenticated(false);
        authTokenDTO.setMessage("Patient not found");
        return authTokenDTO;

    }

    @Transactional
    @Override
    public List<PatientDTO> getAll(){
        return modelMapper.map(patientRepo.findAll(),new TypeToken<List<PatientDTO>>(){}.getType());
    }
    @Transactional
    @Override
    public List<HealthMetricDTO> getHealthMetricsByPatientId(int patientId, HealthMetricType type) {
        List<HealthMetric> allHealthMetrics = healthMetricRepo.findByPatientId(patientId);
        List<HealthMetric> healthMetrics = new ArrayList<>();
        for (HealthMetric healthMetric : allHealthMetrics) {
            if (healthMetric.getType().equals(type)) {
                healthMetrics.add(healthMetric);
            }
        }
        return modelMapper.map(healthMetrics, new TypeToken<List<HealthMetricDTO>>() {}.getType());

    }

    @Override
    public PatientDTO getPatientById(int id) {
        return modelMapper.map(patientRepo.findById(id),PatientDTO.class);
    }

}
