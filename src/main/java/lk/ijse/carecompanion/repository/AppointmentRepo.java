package lk.ijse.carecompanion.repository;

import lk.ijse.carecompanion.entity.Appointment;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentRepo extends JpaRepository<Appointment,Integer> {
    List<Appointment> findByPatientId(int patientId);
    List<Appointment> findByProviderId(int providerId);

    List<Appointment> findAll(Specification<Appointment> spec);
}
