package vn.edu.hust.vha.hims.modules.contract.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContractCreateDTO {
    
    @NotNull(message = "Employee ID is required")
    private UUID employeeId;
    
    @NotNull(message = "Contract type ID is required")
    private UUID contractTypeId;
    
    private UUID decisionId;
    
    @NotNull(message = "Start date is required")
    private LocalDate startDate;
    
    private LocalDate endDate;
    
    @NotNull(message = "Salary is required")
    @DecimalMin(value = "0.0", message = "Salary must be positive")
    private BigDecimal salaryAgreed;
    
    private String contractUrl;
}