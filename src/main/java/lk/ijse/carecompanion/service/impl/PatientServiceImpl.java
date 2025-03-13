package lk.ijse.carecompanion.service.impl;

import lk.ijse.carecompanion.dto.*;
import lk.ijse.carecompanion.entity.*;
import lk.ijse.carecompanion.repository.*;
import lk.ijse.carecompanion.service.PatientService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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


    public void register(PatientRegistrationDTO patientRegistrationDTO){
        Patient patient = modelMapper.map(patientRegistrationDTO, Patient.class);
        Optional<Provider> optProvider = providerRepo.findById(patientRegistrationDTO.getProviderId());
        if (optProvider.isEmpty()) {
            throw new RuntimeException("Provider not found with id: " + patientRegistrationDTO.getProviderId());
        }
        Provider provider = optProvider.get();
        patient.setProvider(provider);
        patientRepo.save(patient);

    }
    public  void update(PatientRegistrationDTO patientRegistrationDTO){
        Patient patient = modelMapper.map(patientRegistrationDTO, Patient.class);
        patientRepo.save(patient);
    }
    public  void delete(int id){
        patientRepo.deleteById(id);
    }
    public PatientDTO getByUserName(String userName){
        return modelMapper.map(patientRepo.findByUserName(userName),PatientDTO.class);
    }
    public List<PatientDTO> getAll(){
        return modelMapper.map(patientRepo.findAll(),new TypeToken<List<PatientDTO>>(){}.getType());
    }

    public void addSymptom(SymptomDTO symptomDTO) {
        Optional<Patient> optPatient = patientRepo.findById(symptomDTO.getPatientId());
        if (!optPatient.isPresent()) {
            throw new RuntimeException("Patient not found with id: " + symptomDTO.getPatientId());
        }
        Patient patient = optPatient.get();

        Symptom symptom = modelMapper.map(symptomDTO, Symptom.class);
        symptom.setPatient(patient);
        symptomRepo.save(symptom);
    }
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
