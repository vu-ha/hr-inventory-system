package vn.edu.hust.vha.hims.common.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import vn.edu.hust.vha.hims.common.enumeration.Gender;
import vn.edu.hust.vha.hims.common.enumeration.MaritalStatus;
import vn.edu.hust.vha.hims.common.mapper.dto.request.EmployeeCreateDTO;
import vn.edu.hust.vha.hims.common.mapper.dto.request.EmployeeUpdateDTO;
import vn.edu.hust.vha.hims.common.mapper.dto.response.EmployeeResponseDTO;
import vn.edu.hust.vha.hims.common.mapper.dto.response.EmployeeSummaryDTO;
import vn.edu.hust.vha.hims.common.mapper.dto.response.PageResponseDTO;
import vn.edu.hust.vha.hims.common.mapper.dto.response.ValidationResponseDTO;
import vn.edu.hust.vha.hims.common.service.EmployeeService;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping
    public ResponseEntity<EmployeeResponseDTO> createEmployee(
            @Valid @RequestBody EmployeeCreateDTO dto) {
        EmployeeResponseDTO response = employeeService.createEmployee(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponseDTO> getEmployeeById(@PathVariable UUID id) {
        EmployeeResponseDTO response = employeeService.getEmployeeById(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeResponseDTO> updateEmployee(
            @PathVariable UUID id,
            @Valid @RequestBody EmployeeUpdateDTO dto) {
        EmployeeResponseDTO response = employeeService.updateEmployee(id, dto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable UUID id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<PageResponseDTO<EmployeeResponseDTO>> getEmployees(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) Gender gender,
            @RequestParam(required = false) MaritalStatus maritalStatus,
            @RequestParam(required = false) UUID departmentId,
            @RequestParam(required = false) UUID positionId,
            @RequestParam(required = false) Short yearJoining,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {
        
        PageResponseDTO<EmployeeResponseDTO> response = employeeService.getEmployeesWithFilters(
            name, email, gender, maritalStatus, departmentId, positionId, yearJoining,
            page, size, sortBy, sortDir
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/search")
    public ResponseEntity<PageResponseDTO<EmployeeResponseDTO>> searchEmployees(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {
        
        PageResponseDTO<EmployeeResponseDTO> response = employeeService.searchEmployees(
            keyword, page, size, sortBy, sortDir
        );
        return ResponseEntity.ok(response);
    }


    @GetMapping("/departments/{departmentId}")
    public ResponseEntity<List<EmployeeSummaryDTO>> getEmployeesByDepartment(
            @PathVariable UUID departmentId) {
        List<EmployeeSummaryDTO> response = employeeService.getEmployeesByDepartment(departmentId);
        return ResponseEntity.ok(response);
    }

    // ==================== VALIDATION ENDPOINTS ====================
    @GetMapping("/check-email")
    public ResponseEntity<ValidationResponseDTO> checkEmailExists(
            @RequestParam String email) {
        ValidationResponseDTO response = employeeService.checkEmailExists(email);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/check-phone")
    public ResponseEntity<ValidationResponseDTO> checkPhoneNumberExists(
            @RequestParam String phoneNumber) {
        ValidationResponseDTO response = employeeService.checkPhoneNumberExists(phoneNumber);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/check-tax-code")
    public ResponseEntity<ValidationResponseDTO> checkTaxCodeExists(
            @RequestParam String taxCode) {
        ValidationResponseDTO response = employeeService.checkTaxCodeExists(taxCode);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/check-insurance-number")
    public ResponseEntity<ValidationResponseDTO> checkInsuranceNumberExists(
            @RequestParam String insuranceNumber) {
        ValidationResponseDTO response = employeeService.checkInsuranceNumberExists(insuranceNumber);
        return ResponseEntity.ok(response);
    }


    @GetMapping("/{id}/check-email")
    public ResponseEntity<ValidationResponseDTO> checkEmailExistsForUpdate(
            @PathVariable UUID id,
            @RequestParam String email) {
        ValidationResponseDTO response = employeeService.checkEmailExistsForUpdate(email, id);
        return ResponseEntity.ok(response);
    }


    @GetMapping("/{id}/check-phone")
    public ResponseEntity<ValidationResponseDTO> checkPhoneNumberExistsForUpdate(
            @PathVariable UUID id,
            @RequestParam String phoneNumber) {
        ValidationResponseDTO response = employeeService.checkPhoneNumberExistsForUpdate(phoneNumber, id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}/check-tax-code")
    public ResponseEntity<ValidationResponseDTO> checkTaxCodeExistsForUpdate(
            @PathVariable UUID id,
            @RequestParam String taxCode) {
        ValidationResponseDTO response = employeeService.checkTaxCodeExistsForUpdate(taxCode, id);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/{id}/check-insurance-number")
    public ResponseEntity<ValidationResponseDTO> checkInsuranceNumberExistsForUpdate(
            @PathVariable UUID id,
            @RequestParam String insuranceNumber) {
        ValidationResponseDTO response = employeeService.checkInsuranceNumberExistsForUpdate(insuranceNumber, id);
        return ResponseEntity.ok(response);
    }
}