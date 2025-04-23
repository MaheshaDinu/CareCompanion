package lk.ijse.carecompanion.service.impl;

import lk.ijse.carecompanion.dto.MedicationScheduleDTO;
import lk.ijse.carecompanion.entity.MedicationSchedule;
import lk.ijse.carecompanion.entity.Patient;
import lk.ijse.carecompanion.repository.MedicationScheduleRepo;
import lk.ijse.carecompanion.repository.PatientRepo;
import lk.ijse.carecompanion.service.MedicationScheduleService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MedicationScheduleServiceImpl implements MedicationScheduleService {
    @Autowired
    MedicationScheduleRepo medicationScheduleRepo;
    @Autowired
    PatientRepo patientRepo;
    @Autowired
    private ModelMapper modelMapper;

    public void save(MedicationScheduleDTO medicationScheduleDTO) {
        Optional<Patient> optPatient = patientRepo.findById(medicationScheduleDTO.getPatientId());
        if (!optPatient.isPresent()) {
            throw new RuntimeException("Patient not found with id: " + medicationScheduleDTO.getPatientId());
        }
        Patient patient = optPatient.get();

        MedicationSchedule medicationSchedule = modelMapper.map(medicationScheduleDTO, MedicationSchedule.class);
        medicationSchedule.setPatient(patient);
        medicationScheduleRepo.save(medicationSchedule);
    }
    public void update(MedicationScheduleDTO medicationScheduleDTO) {
        MedicationSchedule medicationSchedule = modelMapper.map(medicationScheduleDTO, MedicationSchedule.class);
        medicationScheduleRepo.save(medicationSchedule);
    }
    public void delete(int id){
        medicationScheduleRepo.deleteById(id);
    }
    public List<MedicationScheduleDTO> getMedicationSchedulesByPatientId(int patientId) {
        List<MedicationSchedule> medicationSchedules = medicationScheduleRepo.findByPatientId(patientId);
        return medicationSchedules.stream().map(medicationSchedule -> modelMapper.map(medicationSchedule, MedicationScheduleDTO.class)).toList();
    }

    @Override
    public MedicationScheduleDTO getMedicationScheduleById(int id) {
        MedicationSchedule medicationSchedule = medicationScheduleRepo.findById(id).orElse(null);
        return modelMapper.map(medicationSchedule, MedicationScheduleDTO.class);
    }
}
