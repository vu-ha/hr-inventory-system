package vn.edu.hust.vha.hims.modules.organization.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.edu.hust.vha.hims.common.enumeration.AppointmentStatus;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentResponseDTO {
	private UUID appointmentId;
	private UUID employeeId;
	private String employeeFullName;
	
	private UUID decisionId;
	
	private UUID positionId;
	private String positionName;
	
	private UUID departmentId;
	private String departmentName;
	
	private AppointmentStatus status;
	
	private boolean isPrimary;
	private BigDecimal salaryPercentage;
	
	private LocalDate startDate;
	private LocalDate endDate;
	
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	    
	private UUID createdByUUID;
}
