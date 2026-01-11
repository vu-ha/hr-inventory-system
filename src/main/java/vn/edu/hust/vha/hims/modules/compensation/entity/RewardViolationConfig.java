package vn.edu.hust.vha.hims.modules.compensation.entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.edu.hust.vha.hims.common.entity.BaseEntity;
import vn.edu.hust.vha.hims.common.enumeration.RewardViolationType;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "reward_violation_config", schema = "hrm")
@Getter
@Setter
public class RewardViolationConfig extends BaseEntity {

    @Column(name = "code", unique = true, nullable = false, length = 50)
    private String code;

    @Column(name = "name", nullable = false, length = 255)
    private String name;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, length = 10)
    private RewardViolationType type; // REWARD or VIOLATION

    @Column(name = "base_amount")
    private BigDecimal baseAmount;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "is_active")
    private Boolean isActive = true;
}