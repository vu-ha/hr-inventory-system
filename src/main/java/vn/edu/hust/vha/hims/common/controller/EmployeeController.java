package vn.edu.hust.vha.hims.common.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import vn.edu.hust.vha.hims.common.mapper.dto.request.EmployeeCreateDTO;
import vn.edu.hust.vha.hims.common.mapper.dto.response.EmployeeResponseDTO;
import vn.edu.hust.vha.hims.common.service.EmployeeService;

@RestController
@RequestMapping("/api/v1/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    /**
     * API tạo mới nhân viên
     */
    @PostMapping
    public ResponseEntity<EmployeeResponseDTO> createEmployee(@Valid @RequestBody EmployeeCreateDTO dto) {
        EmployeeResponseDTO response = employeeService.createEmployee(dto);
        // Trả về mã 201 Created cùng dữ liệu đã tạo
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}