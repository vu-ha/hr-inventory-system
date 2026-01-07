package vn.edu.hust.vha.hims.modules.contract.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import lombok.*;
import vn.edu.hust.vha.hims.common.enumeration.ContractStatus;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContractResponseDTO {
    
    private UUID contractId;
    
    private UUID employeeId;
    private String employeeFullName;
    
    private UUID contractTypeId;
    private String contractTypeName;
    
    private UUID decisionId;
    private String decisionNumber;
    

    private BigDecimal salaryAgreed;
    private ContractStatus status;
    private LocalDate startDate;
    private LocalDate endDate;
    private String contractUrl;
    
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}