package lk.ijse.carecompanion.entity;

import jakarta.persistence.*;
import lk.ijse.carecompanion.enums.Frequency;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class MedicationSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String medicationName;
    @Column(nullable = false)
    private String dosage;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Frequency frequency;
    @Column(nullable = false)
    private LocalDate startDate;
    @Column(nullable = false)
    private LocalDate endDate;
    @Column(nullable = false)
    private LocalTime reminderTime;
    @ManyToOne
    @JoinColumn(name = "patient_id",nullable = false)
    private Patient patient;

}
