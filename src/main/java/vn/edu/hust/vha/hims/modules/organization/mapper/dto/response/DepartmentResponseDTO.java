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
public class DepartmentResponseDTO {
	private UUID departmentId;
	private String departmentName;
	private String manangerFullName;
	private UUID managerId;
	private int numberCount;
	private int h;
}
