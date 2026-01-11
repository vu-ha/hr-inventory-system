package vn.edu.hust.vha.hims.modules.payroll.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import vn.edu.hust.vha.hims.common.entity.BaseEntity;
import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "allowance_type", schema = "hrm")
@Getter @Setter
public class AllowanceType extends BaseEntity {
    @Column(name = "name", nullable = false)
    private String name;
    
    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "is_taxable")
    private Boolean isTaxable = true;
    
    @Column(name = "description")
    private String description;
}