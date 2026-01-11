package vn.edu.hust.vha.hims.modules.organization.mapper.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.edu.hust.vha.hims.common.enumeration.DecisionType;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DecisionResponseDTO {
	private UUID decisionId;
	private String decisionNumber;
	private DecisionType decisionType;
	private String content;
	private String decisionURL;
	
	private String employeeFullName;
	private UUID employeeId;
	
	private String signerFullName;
	private UUID signerId;


	private LocalDate signedDate;
	private LocalDate effectiveDate;
	private LocalDate expiredDate;
	
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    private UUID createdByUUID;
}
