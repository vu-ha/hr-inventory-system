package vn.edu.hust.vha.hims.modules.organization.entity;

import java.util.List;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "position", schema = "hrm") 
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Position {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(
        name = "id",
        updatable = false,
        nullable = false,
        columnDefinition = "UUID"
    )
    private UUID id;
    
    @Column(name = "name", nullable = false, unique = true)
    private String name;
    
    @Column(name = "description", nullable = false)
    private String description;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_grade_id", nullable = false)
    private JobGrade jobGrade;
    
    @Column(name = "management_level", nullable = false)
    private Integer managementLevel;
    
    @OneToMany(
    		mappedBy = "position",
    		//cascade = CascadeType.ALL, 
            fetch = FetchType.LAZY
    )
    private List<Appointment> appointments; 
}
