package vn.edu.hust.vha.hims.modules.organization.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import vn.edu.hust.vha.hims.common.entity.Employee;
import vn.edu.hust.vha.hims.common.enumeration.ProjectRole;
import vn.edu.hust.vha.hims.common.enumeration.ProjectStatus;
import vn.edu.hust.vha.hims.common.key.ProjectMemberId;
import vn.edu.hust.vha.hims.common.repository.EmployeeRepository;
import vn.edu.hust.vha.hims.modules.organization.entity.Project;
import vn.edu.hust.vha.hims.modules.organization.entity.ProjectMember;
import vn.edu.hust.vha.hims.modules.organization.mapper.dto.request.ProjectCreateDTO;
import vn.edu.hust.vha.hims.modules.organization.mapper.dto.response.ProjectMemberResponseDTO;
import vn.edu.hust.vha.hims.modules.organization.mapper.dto.response.ProjectResponseDTO;
import vn.edu.hust.vha.hims.modules.organization.mapper.dto.response.ProjectSummaryDTO;
import vn.edu.hust.vha.hims.modules.organization.repository.ProjectMemberRepository;
import vn.edu.hust.vha.hims.modules.organization.repository.ProjectRepository;
import vn.edu.hust.vha.hims.modules.organization.service.ProjectService;
@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService{
	private final ProjectRepository projectRepository;
	private final ProjectMemberRepository projectMemberRepository;
	private final EmployeeRepository employeeRepository;
	
	
	@Override
	@Transactional
	public ProjectResponseDTO createProject(ProjectCreateDTO dto) {
		if (dto.getManagerId() == null) {
	        throw new RuntimeException("Lỗi: managerId truyền vào bị null!");
	    }
		
        Employee manager = employeeRepository.getReferenceById(dto.getManagerId());

        Project project = Project.builder()
                .name(dto.getName())
                .startDate(dto.getStartDate())
                .expectedEndDate(dto.getExpectedEndDate())
                .status(ProjectStatus.PLANNED)
                .manager(manager) 
                .build();

        Project savedProject = projectRepository.save(project);


        ProjectMember pmMember = ProjectMember.builder()
                .project(savedProject.getId())
                .employee(dto.getManagerId())
                .roleInProject(ProjectRole.PM)
                .joinDate(LocalDate.now())
                .build();
        projectMemberRepository.save(pmMember);

        return ProjectResponseDTO.builder()
                .projectId(savedProject.getId())
                .name(savedProject.getName())
                .startDate(savedProject.getStartDate())
                .expectedEndDate(savedProject.getExpectedEndDate())
                .status(savedProject.getStatus())
                .managerId(dto.getManagerId())
                .build();
    }
	
	@Override
	@Transactional
	public List<ProjectMemberResponseDTO> addMembersToProject(UUID projectId, List<UUID> employeeIds) {
	    Project project = projectRepository.findById(projectId)
	            .orElseThrow(() -> new RuntimeException("Dự án không tồn tại"));

	    List<ProjectMember> membersToSave = new ArrayList<>();

	    for (UUID empId : employeeIds) {
	        if (projectMemberRepository.existsByProjectAndEmployee(projectId, empId)) continue;

	        ProjectMember member = ProjectMember.builder()
	                .project(projectId)
	                .employee(empId)
	                .roleInProject(ProjectRole.MEMBER) 
	                .joinDate(LocalDate.now())
	                .build();
	        
	        membersToSave.add(member);
	    }

	    projectMemberRepository.saveAll(membersToSave);
	    return convertToResponseList(membersToSave); 
	}
	
	@Override
	@Transactional
	public ProjectMemberResponseDTO updateMemberRole(UUID projectId, UUID employeeId, String newRole) {
	    ProjectMemberId id = new ProjectMemberId(projectId, employeeId);
	    ProjectMember member = projectMemberRepository.findById(id)
	            .orElseThrow(() -> new RuntimeException("Thành viên không tồn tại trong dự án"));

	    member.setRoleInProject(ProjectRole.valueOf(newRole.toUpperCase()));
	    
	    return convertToResponseDTO(member);
	}
	
	
	private ProjectMemberResponseDTO convertToResponseDTO(ProjectMember member) {
	    Employee emp = member.getEmployeeEntity(); // Đã có nhờ JOIN FETCH trong Repository
	    
	    return ProjectMemberResponseDTO.builder()
	            .projectId(member.getProject())
	            .employeeId(member.getEmployee())
	            .employeeName(emp != null ? (emp.getFirstName() + " " + emp.getLastName()) : "")
	            .email(emp != null ? emp.getEmail() : "")        
	            .phoneNumber(emp != null ? emp.getPhoneNumber() : "") 
	            .roleInProject(member.getRoleInProject() != null ? member.getRoleInProject().name() : "")
	            .joinDate(member.getJoinDate())
	            .build();
	}
	
	private List<ProjectMemberResponseDTO> convertToResponseList(List<ProjectMember> members) {
	    if (members == null) return Collections.emptyList();
	    return members.stream()
	            .map(this::convertToResponseDTO)
	            .collect(Collectors.toList());
	}
	
	/*
	 * Xóa thành viên ra khỏi dự án
	 */
	@Override
	@Transactional
	public void removeMemberFromProject(UUID projectId, UUID employeeId) {
	    ProjectMemberId id = new ProjectMemberId(projectId, employeeId);
	    if (!projectMemberRepository.existsById(id)) {
	        throw new RuntimeException("Thành viên không tồn tại trong dự án này");
	    }
	    projectMemberRepository.deleteById(id);
	}
	
	/*
	 * Xóa dự án
	 */
	@Override
	@Transactional
	public void deleteProject(UUID projectId) {
	    if (!projectRepository.existsById(projectId)) {
	        throw new RuntimeException("Dự án không tồn tại");
	    }
	    projectRepository.deleteById(projectId);
	}
	
	/*
	 * Xem tất cả các project
	 */
	@Override
	@Transactional
	public List<ProjectSummaryDTO> getAllProjectSummaries() {
	    return projectRepository.getAllProjectSummaries();
	}
	
	@Override
	@Transactional
	public List<ProjectSummaryDTO> getMyProjectSummaries(UUID employeeId) {
	    return projectRepository.getMyProjectSummaries(employeeId);
	}
	
	@Override
	@Transactional
	public List<ProjectMemberResponseDTO> getMembersByProjectId(UUID projectId) {
	    if (!projectRepository.existsById(projectId)) {
	        throw new RuntimeException("Dự án không tồn tại");
	    }

	    List<ProjectMember> members = projectMemberRepository.findAllByProjectId(projectId);
	    return convertToResponseList(members);
	}
}
