package com.nhom2.qnu.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbl_doctor")
public class Doctor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "doctor_id", length = 36, nullable = false)
    private String doctorId;

    @Column(name = "doctor_name", length = 50, nullable = false)
    private String doctorName;

    @Column(name = "specialization", length = 50, nullable = false)
    private String specialization ;

    @Column(name = "contact_number", length = 12, nullable = false)
    private String contactNumber;

    @Column(name = "email", length = 50, nullable = false)
    private String email;

    @OneToMany(mappedBy ="doctor")
    public Set<AppointmentSchedules> appointmentSchedules = new HashSet<>();

    @ManyToMany(mappedBy = "doctors", fetch = FetchType.LAZY)
    private Set<EHealthRecords> eHealthRecords = new HashSet<>();
}
