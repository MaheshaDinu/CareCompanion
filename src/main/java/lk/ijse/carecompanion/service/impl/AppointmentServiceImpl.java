package lk.ijse.carecompanion.service.impl;

import lk.ijse.carecompanion.dto.AppointmentDTO;
import lk.ijse.carecompanion.entity.Appointment;
import lk.ijse.carecompanion.entity.Patient;
import lk.ijse.carecompanion.entity.Provider;
import lk.ijse.carecompanion.repository.AppointmentRepo;
import lk.ijse.carecompanion.repository.PatientRepo;
import lk.ijse.carecompanion.repository.ProviderRepo;
import lk.ijse.carecompanion.service.AppointmentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public void addAppointment(AppointmentDTO appointmentDTO){
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
}
