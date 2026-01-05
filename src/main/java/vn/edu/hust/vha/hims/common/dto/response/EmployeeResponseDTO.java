package vn.edu.hust.vha.hims.common.dto.response;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.edu.hust.vha.hims.common.enumeration.Gender;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeResponseDTO {
	private UUID employeeID;
	private String fullName;
	private Gender gender;
	private String email;
	private String phoneNumber;
	private String positionName;
	private String departmentName;
}
