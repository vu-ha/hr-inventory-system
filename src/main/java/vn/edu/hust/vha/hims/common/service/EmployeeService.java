package vn.edu.hust.vha.hims.common.service;

import vn.edu.hust.vha.hims.common.mapper.dto.request.EmployeeCreateDTO;
import vn.edu.hust.vha.hims.common.mapper.dto.response.EmployeeResponseDTO;

public interface EmployeeService {
	public EmployeeResponseDTO createEmployee(EmployeeCreateDTO dto);
}
