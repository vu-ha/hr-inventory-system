package vn.edu.hust.vha.hims.modules.organization.mapper.dto.request;

import java.math.BigDecimal;
import java.time.LocalDate;
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
public class AppointmentCreateDTO {
	private UUID replacedAppointmentId;
	private UUID employeeId;
	private String employeeFullName;
	
	private UUID decisionId;
	private UUID positionId;	
	private UUID departmentId;

	private boolean isPrimary;
	private BigDecimal salaryPercentage;
	
	private AppointmentStatus status;
	
	private LocalDate startDate;
	private LocalDate endDate;
}
