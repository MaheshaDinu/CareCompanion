package lk.ijse.carecompanion.entity;

import jakarta.persistence.*;
import lk.ijse.carecompanion.enums.HealthMetricType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
@AllArgsConstructor
@Data
@Entity
public class HealthMetric {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private HealthMetricType type;

    // For single-value metrics (glucose, heart rate, weight)
    @Column(nullable = true)  // Ensure matches DB schema
    private Double value;

    @Column(nullable = true)  // Add explicit nullable
    private Integer systolic;

    @Column(nullable = true)  // Add explicit nullable
    private Integer diastolic;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    private String notes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    public HealthMetric() {
        this.timestamp = LocalDateTime.now();
    }

    // Add validation logic
    @PrePersist
    @PreUpdate
    private void validate() {
        if (type == HealthMetricType.BLOOD_PRESSURE) {
            if (systolic == null || diastolic == null) {
                throw new IllegalArgumentException(
                        "Both systolic and diastolic values are required for blood pressure"
                );
            }
        } else {
            if (value == null) {
                throw new IllegalArgumentException(
                        "Value is required for " + type
                );
            }
        }
    }
}

