package com.nhom2.qnu.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbl_patients")
public class Patients implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "patient_id", length = 36, nullable = false)
    private String patientId;

    @Column(name = "full_name", length = 50, nullable = false)
    private String fullName;

    @Column(name = "contact_number", length = 12, nullable = false)
    private  String contactNumber;

    @Column(name = "email", length = 50, nullable = false,unique = true)
    private  String email;

    @Column(name = "date_of_birth", nullable = false)
    private Date dateOfBirth;

    @Column(name = "address", length = 50, nullable = false)
    private String address;

    @Column(name = "other_info", nullable = false)
    private String otherInfo;

    @OneToMany(mappedBy ="patients")
    public Set<AppointmentSchedules> appointmentSchedules = new HashSet<>();

    @OneToMany(mappedBy = "patient")
    private Set<PrescriptionHistory> prescriptionHistory = new HashSet<>();

    @OneToOne(mappedBy = "patient", cascade = CascadeType.ALL)
    private EHealthRecords eHealthRecords;

    @OneToMany(mappedBy = "patient")
    private Set<PaymentDetails> paymentDetails = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinTable(name = "tbl_patient_service",
            joinColumns = @JoinColumn(name = "patient_id", referencedColumnName = "patient_id"),
            inverseJoinColumns = @JoinColumn(name = "service_id", referencedColumnName = "service_id"))
    private Set<Services> services = new HashSet<>();

}
