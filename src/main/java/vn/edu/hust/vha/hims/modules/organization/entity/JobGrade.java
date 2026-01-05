package vn.edu.hust.vha.hims.modules.organization.entity;

import java.util.List;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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

@Entity
@Table(name = "job_grade", schema = "hrm") 
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobGrade {
	@Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(
        name = "id",
        updatable = false,
        nullable = false,
        columnDefinition = "UUID"
    )
	private UUID id;
	
	@Column(name = "code", nullable = false, unique = true)
	private String code;
	
	@Column(name = "name", nullable = false)
	private String name;
	
	@Column(name = "description", nullable = false)
	private String description;
	
	@Column(name = "min_salary", nullable = false)
	private double minSalary;
	
	@Column(name = "max_salary", nullable = false)
	private double maxSalary;
	
	@OneToMany(
    		mappedBy = "jobGrade",
    		//cascade = CascadeType.ALL, 
            fetch = FetchType.LAZY
    )
    private List<Position> positions;
}
