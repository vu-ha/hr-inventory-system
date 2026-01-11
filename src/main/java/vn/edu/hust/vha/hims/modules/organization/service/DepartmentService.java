package vn.edu.hust.vha.hims.modules.organization.service;

import java.util.UUID;

import vn.edu.hust.vha.hims.modules.organization.mapper.dto.response.DepartmentDashboardDTO;


public interface DepartmentService {
	public DepartmentDashboardDTO getDepartmentDashboard(UUID departmentId);
}
