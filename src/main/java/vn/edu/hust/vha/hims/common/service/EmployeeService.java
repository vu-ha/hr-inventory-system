package vn.edu.hust.vha.hims.common.service;

import java.util.List;
import java.util.UUID;

import vn.edu.hust.vha.hims.common.enumeration.Gender;
import vn.edu.hust.vha.hims.common.enumeration.MaritalStatus;
import vn.edu.hust.vha.hims.common.mapper.dto.request.EmployeeCreateDTO;
import vn.edu.hust.vha.hims.common.mapper.dto.request.EmployeeUpdateDTO;
import vn.edu.hust.vha.hims.common.mapper.dto.response.EmployeeResponseDTO;
import vn.edu.hust.vha.hims.common.mapper.dto.response.EmployeeSummaryDTO;
import vn.edu.hust.vha.hims.common.mapper.dto.response.PageResponseDTO;
import vn.edu.hust.vha.hims.common.mapper.dto.response.ValidationResponseDTO;

public interface EmployeeService {
	public EmployeeResponseDTO createEmployee(EmployeeCreateDTO dto);
	EmployeeResponseDTO getEmployeeById(UUID id);
    
    EmployeeResponseDTO updateEmployee(UUID id, EmployeeUpdateDTO dto);
    
    void deleteEmployee(UUID id);
    
    PageResponseDTO<EmployeeResponseDTO> searchEmployees(String keyword, int page, int size, String sortBy, String sortDir);
    
    PageResponseDTO<EmployeeResponseDTO> getEmployeesWithFilters(
        String name,
        String email,
        Gender gender,
        MaritalStatus maritalStatus,
        UUID departmentId,
        UUID positionId,
        Short yearJoining,
        int page,
        int size,
        String sortBy,
        String sortDir
    );
    
    List<EmployeeSummaryDTO> getEmployeesByDepartment(UUID departmentId);
    
    // Validation
    ValidationResponseDTO checkEmailExists(String email);
    
    ValidationResponseDTO checkPhoneNumberExists(String phoneNumber);
    
    ValidationResponseDTO checkTaxCodeExists(String taxCode);
    
    ValidationResponseDTO checkInsuranceNumberExists(String insuranceNumber);
    
    // Validation for updat
    ValidationResponseDTO checkEmailExistsForUpdate(String email, UUID employeeId);
    
    ValidationResponseDTO checkPhoneNumberExistsForUpdate(String phoneNumber, UUID employeeId);
    
    ValidationResponseDTO checkTaxCodeExistsForUpdate(String taxCode, UUID employeeId);
    
    ValidationResponseDTO checkInsuranceNumberExistsForUpdate(String insuranceNumber, UUID employeeId);
}
