package vn.edu.hust.vha.hims.modules.organization.entity;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.edu.hust.vha.hims.common.entity.Employee;

@Entity
@Table(name = "position_history", schema = "hrm") 
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PositionHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(
        name = "id",
        updatable = false,
        nullable = false,
        columnDefinition = "UUID"
    )
    private UUID id;
    
    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;
    
    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "position_id")
    private Position position;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    private Employee employee;
}
