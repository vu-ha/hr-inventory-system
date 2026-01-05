package vn.edu.hust.vha.hims.common.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import vn.edu.hust.vha.hims.common.dto.response.EmployeeResponseDTO;
import vn.edu.hust.vha.hims.common.service.impl.EmployeeServiceImpl;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeeController {
	private final EmployeeServiceImpl employeeServiceImpl;
	
	@GetMapping("/{departmentId}")
    public ResponseEntity<List<EmployeeResponseDTO>> getEmployeesByDept(
            @PathVariable UUID departmentId) {
        
        List<EmployeeResponseDTO> employees = employeeServiceImpl.getEmployeesByDepartment(departmentId);
        
        if (employees.isEmpty()) {
            return ResponseEntity.noContent().build(); // Trả về 204 nếu không có dữ liệu
        }
        
        return ResponseEntity.ok(employees); // Trả về 200 OK kèm danh sách
    }
}
