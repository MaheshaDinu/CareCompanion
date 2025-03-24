package lk.ijse.carecompanion.entity;

import jakarta.persistence.*;
import lk.ijse.carecompanion.enums.Severity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Symptom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    private String description;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Severity severity;
    private LocalDateTime date;
    private String notes;
    @ManyToOne
    @JoinColumn(name = "patient_id",nullable = false)
    private Patient patient;


}
