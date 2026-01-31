package vn.edu.hust.vha.hims.common.mapper.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.edu.hust.vha.hims.common.enumeration.Gender;
import vn.edu.hust.vha.hims.common.enumeration.MaritalStatus;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeUpdateDTO {
    private String firstName;
    private String lastName;
    private Gender gender;
    
    @Email(message = "Email should be valid")
    private String email;
    
    @Pattern(regexp = "^[0-9]{10,11}$", message = "Phone number should be 10-11 digits")
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