package lk.ijse.carecompanion.service.impl;

import lk.ijse.carecompanion.dto.*;
import lk.ijse.carecompanion.entity.HealthMetric;
import lk.ijse.carecompanion.entity.MedicationSchedule;
import lk.ijse.carecompanion.entity.Patient;
import lk.ijse.carecompanion.entity.Symptom;
import lk.ijse.carecompanion.repository.HealthMetricRepo;
import lk.ijse.carecompanion.repository.MedicationScheduleRepo;
import lk.ijse.carecompanion.repository.PatientRepo;
import lk.ijse.carecompanion.repository.SymptomRepo;
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
    HealthMetricRepo healthMetricRepo;
    @Autowired
    MedicationScheduleRepo medicationScheduleRepo;
    @Autowired
    SymptomRepo symptomRepo;
    @Autowired
    ModelMapper modelMapper;


    public void register(PatientRegistrationDTO patientRegistrationDTO){
        Patient patient = modelMapper.map(patientRegistrationDTO, Patient.class);
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
    public void addMedicationSchedule(MedicationScheduleDTO medicationScheduleDTO) {
        Optional<Patient> optPatient = patientRepo.findById(medicationScheduleDTO.getPatientId());
        if (!optPatient.isPresent()) {
            throw new RuntimeException("Patient not found with id: " + medicationScheduleDTO.getPatientId());
        }
        Patient patient = optPatient.get();

        MedicationSchedule medicationSchedule = modelMapper.map(medicationScheduleDTO, MedicationSchedule.class);
        medicationSchedule.setPatient(patient);
        medicationScheduleRepo.save(medicationSchedule);
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

}
