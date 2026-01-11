package vn.edu.hust.vha.hims.modules.organization.mapper.dto.response;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PositionResponseDTO {
	private UUID id;
	private String name;
	private String description;
	private String jobGradeName;
	private int managementLevel;
}
