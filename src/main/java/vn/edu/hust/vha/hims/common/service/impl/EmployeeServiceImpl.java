package vn.edu.hust.vha.hims.common.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import vn.edu.hust.vha.hims.common.entity.Employee;
import vn.edu.hust.vha.hims.common.enumeration.Gender;
import vn.edu.hust.vha.hims.common.enumeration.MaritalStatus;
import vn.edu.hust.vha.hims.common.exception.ResourceNotFoundException;
import vn.edu.hust.vha.hims.common.mapper.EmployeeMapper;
import vn.edu.hust.vha.hims.common.mapper.dto.request.EmployeeCreateDTO;
import vn.edu.hust.vha.hims.common.mapper.dto.request.EmployeeUpdateDTO;
import vn.edu.hust.vha.hims.common.mapper.dto.response.EmployeeResponseDTO;
import vn.edu.hust.vha.hims.common.mapper.dto.response.EmployeeSummaryDTO;
import vn.edu.hust.vha.hims.common.mapper.dto.response.PageResponseDTO;
import vn.edu.hust.vha.hims.common.mapper.dto.response.ValidationResponseDTO;
import vn.edu.hust.vha.hims.common.repository.EmployeeRepository;
import vn.edu.hust.vha.hims.common.service.EmployeeService;

@Service
@RequiredArgsConstructor
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;

    @Override
    public EmployeeResponseDTO createEmployee(EmployeeCreateDTO dto) {
        checkUniqueFieldsForCreate(dto);
        
        Employee employee = Employee.builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .gender(dto.getGender())
                .email(dto.getEmail())
                .phoneNumber(dto.getPhoneNumber())
                .maritalStatus(dto.getMaritalStatus())
                .permanentAddress(dto.getPermanentAddress())
                .bankAccount(dto.getBankAccount())
                .bankName(dto.getBankName())
                .taxCode(dto.getTaxCode())
                .socialInsuranceNumber(dto.getSocialInsuranceNumber())
                .hometown(dto.getHometown())
                .yearJoining(dto.getYearJoining())
                .build();
        
        Employee saved = employeeRepository.save(employee);
        employeeRepository.flush();     
        return employeeMapper.toResponse(saved);       
    }
    
    private void checkUniqueFieldsForCreate(EmployeeCreateDTO dto) {
        if (employeeRepository.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("Email already exists: " + dto.getEmail());
        }
        if (employeeRepository.existsByPhoneNumber(dto.getPhoneNumber())) {
            throw new IllegalArgumentException("Phone number already exists: " + dto.getPhoneNumber());
        }
        if (employeeRepository.existsByTaxCode(dto.getTaxCode())) {
            throw new IllegalArgumentException("Tax code already exists: " + dto.getTaxCode());
        }
        if (employeeRepository.existsBySocialInsuranceNumber(dto.getSocialInsuranceNumber())) {
            throw new IllegalArgumentException("Social insurance number already exists: " + dto.getSocialInsuranceNumber());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public EmployeeResponseDTO getEmployeeById(UUID id) {
        Employee employee = findEmployeeById(id);
        return employeeMapper.toResponse(employee);
    }

    @Override
    public EmployeeResponseDTO updateEmployee(UUID id, EmployeeUpdateDTO dto) {
        Employee employee = findEmployeeById(id);
        
        if (dto.getEmail() != null || dto.getPhoneNumber() != null || 
            dto.getTaxCode() != null || dto.getSocialInsuranceNumber() != null) {
            validateUniqueFields(dto.getEmail(), dto.getPhoneNumber(), 
                                dto.getTaxCode(), dto.getSocialInsuranceNumber(), id);
        }
        
        employeeMapper.updateEntityFromDto(dto, employee);
        Employee updated = employeeRepository.save(employee);
        return employeeMapper.toResponse(updated);
    }

    @Override
    public void deleteEmployee(UUID id) {
        Employee employee = findEmployeeById(id);      
        // Check if employee has any relationships
        if (!employee.getAppointments().isEmpty()) {
            throw new IllegalStateException("Cannot delete employee with existing appointments");
        }
        if (!employee.getContracts().isEmpty()) {
            throw new IllegalStateException("Cannot delete employee with existing contracts");
        }
        
        employeeRepository.delete(employee);
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponseDTO<EmployeeResponseDTO> searchEmployees(
            String keyword, int page, int size, String sortBy, String sortDir) {
        
        Sort sort = sortDir.equalsIgnoreCase("asc") 
            ? Sort.by(sortBy).ascending() 
            : Sort.by(sortBy).descending();
        
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Employee> employeePage = employeeRepository.searchEmployees(keyword, pageable);
        
        return mapToPageResponse(employeePage);
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponseDTO<EmployeeResponseDTO> getEmployeesWithFilters(
            String name, String email, Gender gender, MaritalStatus maritalStatus,
            UUID departmentId, UUID positionId, Short yearJoining,
            int page, int size, String sortBy, String sortDir) {
        
        Sort sort = sortDir.equalsIgnoreCase("asc") 
            ? Sort.by(sortBy).ascending() 
            : Sort.by(sortBy).descending();
        
        Pageable pageable = PageRequest.of(page, size, sort);

        String nameParam = (name != null) ? "%" + name.toLowerCase() + "%" : null;
        String emailParam = (email != null) ? "%" + email.toLowerCase() + "%" : null;
        
        Page<Employee> employeePage = employeeRepository.findEmployeesWithFilters(
            nameParam, emailParam, gender, maritalStatus, departmentId, positionId, yearJoining, pageable
        );
        
        return mapToPageResponse(employeePage);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmployeeSummaryDTO> getEmployeesByDepartment(UUID departmentId) {
        return employeeRepository.findAllEmployee(departmentId);
    }

    @Override
    @Transactional(readOnly = true)
    public ValidationResponseDTO checkEmailExists(String email) {
        boolean exists = employeeRepository.existsByEmail(email);
        return ValidationResponseDTO.builder()
            .exists(exists)
            .message(exists ? "Email already exists" : "Email is available")
            .build();
    }

    @Override
    @Transactional(readOnly = true)
    public ValidationResponseDTO checkPhoneNumberExists(String phoneNumber) {
        boolean exists = employeeRepository.existsByPhoneNumber(phoneNumber);
        return ValidationResponseDTO.builder()
            .exists(exists)
            .message(exists ? "Phone number already exists" : "Phone number is available")
            .build();
    }

    @Override
    @Transactional(readOnly = true)
    public ValidationResponseDTO checkTaxCodeExists(String taxCode) {
        boolean exists = employeeRepository.existsByTaxCode(taxCode);
        return ValidationResponseDTO.builder()
            .exists(exists)
            .message(exists ? "Tax code already exists" : "Tax code is available")
            .build();
    }

    @Override
    @Transactional(readOnly = true)
    public ValidationResponseDTO checkInsuranceNumberExists(String insuranceNumber) {
        boolean exists = employeeRepository.existsBySocialInsuranceNumber(insuranceNumber);
        return ValidationResponseDTO.builder()
            .exists(exists)
            .message(exists ? "Insurance number already exists" : "Insurance number is available")
            .build();
    }

    @Override
    @Transactional(readOnly = true)
    public ValidationResponseDTO checkEmailExistsForUpdate(String email, UUID employeeId) {
        boolean exists = employeeRepository.existsByEmailAndIdNot(email, employeeId);
        return ValidationResponseDTO.builder()
            .exists(exists)
            .message(exists ? "Email already exists" : "Email is available")
            .build();
    }

    @Override
    @Transactional(readOnly = true)
    public ValidationResponseDTO checkPhoneNumberExistsForUpdate(String phoneNumber, UUID employeeId) {
        boolean exists = employeeRepository.existsByPhoneNumberAndIdNot(phoneNumber, employeeId);
        return ValidationResponseDTO.builder()
            .exists(exists)
            .message(exists ? "Phone number already exists" : "Phone number is available")
            .build();
    }

    @Override
    @Transactional(readOnly = true)
    public ValidationResponseDTO checkTaxCodeExistsForUpdate(String taxCode, UUID employeeId) {
        boolean exists = employeeRepository.existsByTaxCodeAndIdNot(taxCode, employeeId);
        return ValidationResponseDTO.builder()
            .exists(exists)
            .message(exists ? "Tax code already exists" : "Tax code is available")
            .build();
    }

    @Override
    @Transactional(readOnly = true)
    public ValidationResponseDTO checkInsuranceNumberExistsForUpdate(String insuranceNumber, UUID employeeId) {
        boolean exists = employeeRepository.existsBySocialInsuranceNumberAndIdNot(insuranceNumber, employeeId);
        return ValidationResponseDTO.builder()
            .exists(exists)
            .message(exists ? "Insurance number already exists" : "Insurance number is available")
            .build();
    }

    // Helper methods
    private Employee findEmployeeById(UUID id) {
        return employeeRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));
    }

    private void validateUniqueFields(String email, String phoneNumber, 
                                      String taxCode, String insuranceNumber, UUID excludeId) {
        if (excludeId == null) {
            // For create operation
            if (email != null && employeeRepository.existsByEmail(email)) {
                throw new IllegalArgumentException("Email already exists: " + email);
            }
            if (phoneNumber != null && employeeRepository.existsByPhoneNumber(phoneNumber)) {
                throw new IllegalArgumentException("Phone number already exists: " + phoneNumber);
            }
            if (taxCode != null && employeeRepository.existsByTaxCode(taxCode)) {
                throw new IllegalArgumentException("Tax code already exists: " + taxCode);
            }
            if (insuranceNumber != null && employeeRepository.existsBySocialInsuranceNumber(insuranceNumber)) {
                throw new IllegalArgumentException("Social insurance number already exists: " + insuranceNumber);
            }
        } else {
            // For update operation
            if (email != null && employeeRepository.existsByEmailAndIdNot(email, excludeId)) {
                throw new IllegalArgumentException("Email already exists: " + email);
            }
            if (phoneNumber != null && employeeRepository.existsByPhoneNumberAndIdNot(phoneNumber, excludeId)) {
                throw new IllegalArgumentException("Phone number already exists: " + phoneNumber);
            }
            if (taxCode != null && employeeRepository.existsByTaxCodeAndIdNot(taxCode, excludeId)) {
                throw new IllegalArgumentException("Tax code already exists: " + taxCode);
            }
            if (insuranceNumber != null && employeeRepository.existsBySocialInsuranceNumberAndIdNot(insuranceNumber, excludeId)) {
                throw new IllegalArgumentException("Social insurance number already exists: " + insuranceNumber);
            }
        }
    }

    private PageResponseDTO<EmployeeResponseDTO> mapToPageResponse(Page<Employee> employeePage) {
        List<EmployeeResponseDTO> content = employeePage.getContent()
            .stream()
            .map(employeeMapper::toResponse)
            .toList();
        
        return PageResponseDTO.<EmployeeResponseDTO>builder()
            .content(content)
            .pageNumber(employeePage.getNumber())
            .pageSize(employeePage.getSize())
            .totalElements(employeePage.getTotalElements())
            .totalPages(employeePage.getTotalPages())
            .last(employeePage.isLast())
            .first(employeePage.isFirst())
            .build();
    }
}