package vn.edu.hust.vha.hims.modules.contract.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime; // Thêm import này
import java.util.UUID;

import lombok.*;
import vn.edu.hust.vha.hims.common.enumeration.ContractStatus;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContractResponseDTO {
    
    private UUID contractId;
    
    // Thông tin nhân viên
    private UUID employeeId;
    private String employeeFullName;
    
    // Thông tin loại hợp đồng
    private UUID contractTypeId;
    private String contractTypeName;
    
    // Thông tin quyết định (nullable)
    private UUID decisionId;
    private String decisionNumber;
    
    // Thông tin hợp đồng
    private BigDecimal salaryAgreed;
    private ContractStatus status;
    private LocalDate startDate;
    private LocalDate endDate;
    private String contractUrl;
    
    // Metadata - ĐỔI THÀNH LocalDateTime
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}