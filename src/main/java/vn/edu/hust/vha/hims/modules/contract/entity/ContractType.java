package vn.edu.hust.vha.hims.modules.contract.entity;

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
@Table(name = "contract_type", schema = "hrm")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContractType {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(
        name = "id",
        updatable = false,
        nullable = false,
        columnDefinition = "UUID"
    )
    private UUID id;
    
    @Column(name = "name", length = 50)
    private String name;
    
    @Column(name = "is_insurance")
    private Integer isInsurance;
    

    @Column(name = "month_count")
    private Integer monthCount;
    
    @OneToMany(
        mappedBy = "contractType",
        fetch = FetchType.LAZY
    )
    private List<Contract> contracts;
}