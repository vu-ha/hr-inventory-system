package vn.edu.hust.vha.hims.modules.payroll.entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.edu.hust.vha.hims.common.entity.BaseEntity;
import vn.edu.hust.vha.hims.common.entity.Employee;
import vn.edu.hust.vha.hims.common.enumeration.PayrollStatus;

@Entity
@Table(name = "payroll", schema = "hrm") 
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payroll extends BaseEntity{	
	@Column(name = "month", nullable = false)
	private Short month;
	
	@Column(name = "year", nullable = false)
	private Short year;
	
	@Column(name = "gross_salary", nullable = false)
	private BigDecimal grossSalary;
	
	@Column(name = "net_salary", nullable = false)
	private BigDecimal netSalary;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "status", nullable = false)
	private PayrollStatus status;
	
	@Column(name = "note")
	private String note;		
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;
}
