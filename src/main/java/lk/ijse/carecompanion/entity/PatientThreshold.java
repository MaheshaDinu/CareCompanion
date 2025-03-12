package lk.ijse.carecompanion.entity;

import jakarta.persistence.*;
import lk.ijse.carecompanion.enums.HealthMetricType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class PatientThreshold {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private HealthMetricType type;
    @Column(nullable = false)
    private double minValue;
    @Column(nullable = false)
    private double maxValue;
    @ManyToOne
    @JoinColumn(name = "patient_id",nullable = false)
    private Patient patient;
}
