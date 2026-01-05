package vn.edu.hust.vha.hims.modules.organization.entity;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.edu.hust.vha.hims.common.entity.BaseEntity;
import vn.edu.hust.vha.hims.common.entity.Employee;
import vn.edu.hust.vha.hims.common.enumeration.DecisionType;

@Entity
@Table(name = "decision", schema = "hrm") 
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Decision extends BaseEntity{
    @Column(name = "decision_type", nullable = false)
	private DecisionType decisionType;
	
    @Column(name = "decision_number", nullable = false, unique = true)
	private String decisionNumber;
    
    @Column(name = "content", nullable = false)
	private String content;
    
    @Column(name = "decision_url", nullable = false, unique = true)
	private String decisionURL;
	
    @Column(name = "signed_date", nullable = false, unique = true)
	private LocalDate signedDate;
    
    @Column(name = "effective_date", nullable = false, unique = true)
	private LocalDate effectiveDate;
    
    @Column(name = "expired_date", nullable = false, unique = true)
	private LocalDate expiredDate;
	
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false)
	private Employee employee;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "signer_id", nullable = false)
    private Employee signer;
    
    @OneToMany(
            mappedBy = "decision", 
            fetch = FetchType.LAZY
            //cascade = CascadeType.ALL 
        )
    private List<Appointment> appointments;
    
    
}
