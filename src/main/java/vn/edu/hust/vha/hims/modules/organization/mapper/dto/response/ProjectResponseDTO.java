package vn.edu.hust.vha.hims.modules.organization.mapper.dto.response;

import java.time.LocalDate;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.edu.hust.vha.hims.common.enumeration.ProjectStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProjectResponseDTO {
	private UUID projectId;
	private String name;
	private ProjectStatus status;
	private LocalDate startDate;
	private LocalDate expectedEndDate;
	private UUID managerId;
}
