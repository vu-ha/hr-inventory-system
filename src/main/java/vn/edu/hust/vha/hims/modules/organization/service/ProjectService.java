package vn.edu.hust.vha.hims.modules.organization.service;

import java.util.List;
import java.util.UUID;

import vn.edu.hust.vha.hims.modules.organization.mapper.dto.request.ProjectCreateDTO;
import vn.edu.hust.vha.hims.modules.organization.mapper.dto.response.ProjectMemberResponseDTO;
import vn.edu.hust.vha.hims.modules.organization.mapper.dto.response.ProjectResponseDTO;
import vn.edu.hust.vha.hims.modules.organization.mapper.dto.response.ProjectSummaryDTO;

public interface ProjectService {
	ProjectResponseDTO createProject(ProjectCreateDTO dto);
	
	List<ProjectMemberResponseDTO> addMembersToProject(UUID projectId, List<UUID> employeeIds);
	ProjectMemberResponseDTO updateMemberRole(UUID projectId, UUID employeeId, String newRole);
	void removeMemberFromProject(UUID projectId, UUID employeeId);
	void deleteProject(UUID projectId);
	List<ProjectSummaryDTO> getAllProjectSummaries();
	List<ProjectSummaryDTO> getMyProjectSummaries(UUID employeeId);
	List<ProjectMemberResponseDTO> getMembersByProjectId(UUID projectId);
}
