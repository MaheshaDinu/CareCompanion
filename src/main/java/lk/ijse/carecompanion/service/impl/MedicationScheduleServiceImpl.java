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

import java.util.Optional;

@Service
public class MedicationScheduleServiceImpl implements MedicationScheduleService {
    @Autowired
    MedicationScheduleRepo medicationScheduleRepo;
    @Autowired
    PatientRepo patientRepo;
    @Autowired
    private ModelMapper modelMapper;

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
}
