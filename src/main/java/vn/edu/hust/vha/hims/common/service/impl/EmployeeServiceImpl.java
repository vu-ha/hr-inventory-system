package vn.edu.hust.vha.hims.common.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import vn.edu.hust.vha.hims.common.dto.response.EmployeeResponseDTO;
import vn.edu.hust.vha.hims.common.repository.EmployeeRepository;
import vn.edu.hust.vha.hims.common.service.EmployeeService;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Override
    public List<EmployeeResponseDTO> getEmployeesByDepartment(UUID departmentId) {

        List<EmployeeResponseDTO> employees = employeeRepository.findAllEmployee(departmentId);
        return employees;
    }
}
