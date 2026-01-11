package vn.edu.hust.vha.hims.modules.organization.mapper.dto.request;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.edu.hust.vha.hims.common.enumeration.DecisionType;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DecisionCreateDTO {
    @NotNull(message = "Employee ID cannot be null")
    private UUID employeeId;

    @NotBlank(message = "Decision number is required")
    private String decisionNumber;

    @NotNull(message = "Decision type is required")
    private DecisionType decisionType;

    private UUID signerId;
    private String content;
    private String decisionURL;
    
    @NotNull(message = "Effective date is required")
    private LocalDate effectiveDate;
    
    private LocalDate signedDate;
    private LocalDate expiredDate;
}