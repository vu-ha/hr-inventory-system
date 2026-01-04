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
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.edu.hust.vha.hims.common.entity.Employee;

@Entity
@Table(name = "department", schema = "hrm") 
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(
        name = "id",
        updatable = false,
        nullable = false,
        columnDefinition = "UUID"
    )
    private UUID id;
    
    @Column(name = "room_code", nullable = false, unique = true)
    private String roomCode;
    
    @Column(name = "name", nullable = false, unique = true)
    private String name;
    
    @Column(name = "description", nullable = false)
    private String description;
    
    @OneToMany(
    		mappedBy = "department",
            fetch = FetchType.LAZY
    )
    List<Employee> employee;
    
    @OneToOne
    @JoinColumn(name = "manager_id", unique = true)
    private Employee manager;
    
    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Department parent;

    @OneToMany(
    		mappedBy = "parent",
            fetch = FetchType.LAZY
    ) 
    private List<Department> children;
}
