package lk.ijse.carecompanion.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@DiscriminatorValue("PROVIDER")
public class Provider extends Users{
    @Column(nullable = false)
    private String clinicName;
    @Column(nullable = false)
    private String licenseNumber;
    @OneToMany(mappedBy = "provider", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Appointment> appointmentsAsProvider;
    @OneToMany(mappedBy = "provider",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Patient> patients;

    public Provider() {
        super();
    }
}
