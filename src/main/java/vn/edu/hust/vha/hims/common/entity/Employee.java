package vn.edu.hust.vha.hims.common.entity;

import java.util.List;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.edu.hust.vha.hims.common.enumeration.Gender;
import vn.edu.hust.vha.hims.common.enumeration.MaritalStatus;
import vn.edu.hust.vha.hims.modules.notification.entity.NotificationRecipient;
import vn.edu.hust.vha.hims.modules.organization.entity.Appointment;
import vn.edu.hust.vha.hims.modules.organization.entity.Decision;
import vn.edu.hust.vha.hims.modules.organization.entity.Department;

@Entity
@Table(name = "employee", schema = "hrm") 
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(
        name = "id",
        updatable = false,
        nullable = false,
        columnDefinition = "UUID"
    )
    private UUID id;
    
    @Column(name = "first_name", nullable = false)
    private String firstName;
    
    @Column(name = "last_name", nullable = false)
    private String lastName;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = false)
    private Gender gender;
    
    @Column(name = "email", nullable = false, unique = true)
    private String email;
    
    @Column(name = "phone_number", nullable = false, unique = true)
    private String phoneNumber;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "marital_status", nullable = false)
    private MaritalStatus maritalStatus;
    
    @Column(name = "permanent_address", nullable = false)
    private String permanentAddress;
    
    @Column(name = "bank_account", nullable = false)
    private String bankAccount;
    
    @Column(name = "bank_name", nullable = false)
    private String bankName;
    
    @Column(name = "tax_code", nullable = false, unique = true)
    private String taxCode;
    
    @Column(name = "social_insurance_number", nullable = false, unique = true)
    private String socialInsuranceNumber;
    
    @Column(name = "hometown")
    private String hometown;
    
    @Column(name = "year_of_joining")
    private short yearJoining;
    
    @OneToMany(
    		mappedBy = "employee",
    		cascade = CascadeType.ALL, 
            fetch = FetchType.LAZY
    )
    private List<NotificationRecipient> notifications; 
    
    @OneToMany(
    		mappedBy = "employee",
    		cascade = CascadeType.ALL, 
            fetch = FetchType.LAZY
    )
    private List<Decision> decisions; 
    
    @OneToMany(
    		mappedBy = "employee",
    		cascade = CascadeType.ALL, 
            fetch = FetchType.LAZY
    )
    private List<Appointment> appointments; 
    
    @OneToMany(
    		mappedBy = "signer",
            fetch = FetchType.LAZY
    )
    private List<Decision> signedDecisions;
    
    @OneToMany(mappedBy = "manager")
    private List<Department> managedDepartments;
}
