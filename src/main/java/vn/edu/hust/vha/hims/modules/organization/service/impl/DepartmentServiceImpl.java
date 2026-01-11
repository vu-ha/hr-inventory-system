package vn.edu.hust.vha.hims.modules.organization.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import vn.edu.hust.vha.hims.common.mapper.dto.response.EmployeeSummaryDTO;
import vn.edu.hust.vha.hims.common.repository.EmployeeRepository;
import vn.edu.hust.vha.hims.modules.organization.entity.Department;
import vn.edu.hust.vha.hims.modules.organization.mapper.dto.response.DepartmentDashboardDTO;
import vn.edu.hust.vha.hims.modules.organization.repository.DepartmentRepository;
import vn.edu.hust.vha.hims.modules.organization.service.DepartmentService;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final EmployeeRepository employeeRepository;

    @Override
    public DepartmentDashboardDTO getDepartmentDashboard(UUID departmentId) {
        Department dept = departmentRepository.findById(departmentId)
            .orElseThrow(() -> new EntityNotFoundException("Phòng ban không tồn tại"));

        List<EmployeeSummaryDTO> employees = employeeRepository.findAllEmployee(departmentId);

        return new DepartmentDashboardDTO(
            dept.getId(),
            dept.getName(),
            dept.getManager().getFirstName() + " " + dept.getManager().getLastName(),
            employees
        );
    }
}