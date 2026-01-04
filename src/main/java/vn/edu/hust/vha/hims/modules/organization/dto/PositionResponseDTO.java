package vn.edu.hust.vha.hims.modules.organization.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.edu.hust.vha.hims.common.enumeration.PositionLevel;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PositionResponseDTO {
	private UUID id;
	private String name;
	private String description;
	private PositionLevel level;
}
