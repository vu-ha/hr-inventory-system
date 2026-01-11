package vn.edu.hust.vha.hims.modules.organization.mapper.dto.response;

import java.util.List;
import java.util.UUID;

import vn.edu.hust.vha.hims.common.mapper.dto.response.EmployeeSummaryDTO;

public record DepartmentDashboardDTO(
	    UUID departmentId,
	    String departmentName,
	    String managerName, 
	    List<EmployeeSummaryDTO> employees
) {}