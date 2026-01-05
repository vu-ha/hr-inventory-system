package vn.edu.hust.vha.hims.common.service;

import java.util.List;
import java.util.UUID;

import vn.edu.hust.vha.hims.common.dto.response.EmployeeResponseDTO;

public interface EmployeeService {
	public List<EmployeeResponseDTO> getEmployeesByDepartment(UUID departmentId);
}
