package vn.edu.hust.vha.hims.modules.organization.dto.request;

import java.time.LocalDate;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.edu.hust.vha.hims.common.enumeration.ProjectStatus;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProjectCreateDTO {
	private String name;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate startDate;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate expectedEndDate;
	private ProjectStatus status;
	private UUID managerId;
}
