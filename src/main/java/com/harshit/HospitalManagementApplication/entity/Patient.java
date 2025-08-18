package com.harshit.HospitalManagementApplication.entity;

import com.harshit.HospitalManagementApplication.entity.type.BloodGroupType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@ToString
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "patient",uniqueConstraints = {
       //@UniqueConstraint(name = "unique_patient_email",columnNames = {"email"}),
        @UniqueConstraint(name="unique_patient_name_birthdate",columnNames = {"name","birthDate"})
        },
indexes = {@Index(name = "idx_patient_birth_date",columnList = "birthDate")
}
)
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,length = 40)
    private String name;

//    @ToString.Exclude
    private LocalDate birthDate;

    @Column(unique = true,nullable = false)
    private String email;

    private String gender;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createAt;

    @Enumerated(EnumType.STRING)
    private BloodGroupType bloodGroup;

    @OneToOne
    @JoinColumn(name = "patient_insurance_id") // those who has fk or has join column act as Owner side
    private Insurance insurance;

    @OneToMany(mappedBy = "patient")
    private List<Appointment> appointments;

}
