package lk.ijse.carecompanion.service.impl;

import lk.ijse.carecompanion.dto.SymptomDTO;
import lk.ijse.carecompanion.entity.Patient;
import lk.ijse.carecompanion.entity.Symptom;
import lk.ijse.carecompanion.repository.PatientRepo;
import lk.ijse.carecompanion.repository.SymptomRepo;
import lk.ijse.carecompanion.service.SymptomService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SymptomServiceImpl implements SymptomService {
    @Autowired
    private SymptomRepo symptomRepo;
    @Autowired
    private PatientRepo patientRepo;
    @Autowired
    private ModelMapper modelMapper;
    public void save(SymptomDTO symptomDTO) {
        Optional<Patient> optPatient = patientRepo.findById(symptomDTO.getPatientId());
        if (!optPatient.isPresent()) {
            throw new RuntimeException("Patient not found with id: " + symptomDTO.getPatientId());
        }
        Patient patient = optPatient.get();

        Symptom symptom = modelMapper.map(symptomDTO, Symptom.class);
        symptom.setPatient(patient);
        symptomRepo.save(symptom);
    }
    public void update(SymptomDTO symptomDTO) {
        Symptom symptom = modelMapper.map(symptomDTO, Symptom.class);
        symptomRepo.save(symptom);
    }
    public void delete(int id) {
        symptomRepo.deleteById(id);
    }
    public List<SymptomDTO> getSymptomsByPatientId(int patientId) {
        List<Symptom> symptoms = symptomRepo.findByPatientId(patientId);
        return symptoms.stream().map(symptom -> modelMapper.map(symptom, SymptomDTO.class)).toList();
    }
    public SymptomDTO getSymptomById(int id) {
        Optional<Symptom> optSymptom = symptomRepo.findById(id);
        if (!optSymptom.isPresent()) {
            throw new RuntimeException("Symptom not found with id: " + id);
        }
        Symptom symptom = optSymptom.get();
        return modelMapper.map(symptom, SymptomDTO.class);
    }
}
