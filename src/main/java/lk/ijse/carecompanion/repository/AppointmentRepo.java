package lk.ijse.carecompanion.repository;

import lk.ijse.carecompanion.entity.Appointment;
import lk.ijse.carecompanion.enums.AppointmentStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AppointmentRepo extends JpaRepository<Appointment,Integer> {
    List<Appointment> findByPatientId(int patientId);
    List<Appointment> findByProviderId(int providerId);

    List<Appointment> findAll(Specification<Appointment> spec);

    int countByProviderIdAndStatusAndDateTimeAfter(int providerId, AppointmentStatus status, LocalDateTime now);

    int countByProviderIdAndDateTimeBetween(int providerId, LocalDateTime startOfToday, LocalDateTime endOfToday);
    @Query("SELECT a FROM Appointment a " +
            "JOIN FETCH a.patient " +
            "JOIN FETCH a.provider " +
            "WHERE a.provider.id = :providerId " +
            "AND a.status = :status " +
            "AND a.dateTime > :currentTime " +
            "ORDER BY a.dateTime ASC")
    List<Appointment> findConfirmedUpcomingAppointments(
            @Param("providerId") int providerId,
            @Param("status") AppointmentStatus status,
            @Param("currentTime") LocalDateTime currentTime,
            Pageable pageable
    );
}
