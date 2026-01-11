package vn.edu.hust.vha.hims.common.mapper.dto.response;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.edu.hust.vha.hims.common.enumeration.Gender;
import vn.edu.hust.vha.hims.common.enumeration.MaritalStatus;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeResponseDTO {
	private UUID employeeID;
    private String firstName;
    private String lastName;
    private Gender gender;
    private String email;
    private String phoneNumber;
    private MaritalStatus maritalStatus;
    private String permanentAddress;
    private String bankAccount;
    private String bankName;
    private String taxCode;
    private String socialInsuranceNumber;
    private String hometown;
    private Short yearJoining;  
}
