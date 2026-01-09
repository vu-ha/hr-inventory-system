package vn.edu.hust.vha.hims.modules.organization.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import vn.edu.hust.vha.hims.modules.organization.dto.request.ProjectCreateDTO;
import vn.edu.hust.vha.hims.modules.organization.dto.response.ProjectMemberResponseDTO;
import vn.edu.hust.vha.hims.modules.organization.dto.response.ProjectResponseDTO;
import vn.edu.hust.vha.hims.modules.organization.dto.response.ProjectSummaryDTO;
import vn.edu.hust.vha.hims.modules.organization.projection.ProjectSummary;
import vn.edu.hust.vha.hims.modules.organization.service.ProjectService;

@RestController
@RequestMapping("/api/v1/projects")
@RequiredArgsConstructor
public class ProjectController {
    private final ProjectService projectService;
    
    @PostMapping
    public ResponseEntity<ProjectResponseDTO> createProject(@Valid @RequestBody ProjectCreateDTO dto) {
        ProjectResponseDTO response = projectService.createProject(dto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // 1. Thêm nhanh danh sách (Mặc định MEMBER)
    @PostMapping("/{projectId}/bulk-add") // Thêm {projectId} vào đây
    public ResponseEntity<?> quickAdd(
            @PathVariable UUID projectId, 
            @RequestBody List<UUID> employeeIds) {
        return ResponseEntity.ok(projectService.addMembersToProject(projectId, employeeIds));
    }

    // 2. Cập nhật Role cho từng người
    @PatchMapping("/{projectId}/members/{employeeId}/role") //
    public ResponseEntity<?> updateRole(
            @PathVariable UUID projectId, 
            @PathVariable UUID employeeId, 
            @RequestParam String role) {
        return ResponseEntity.ok(projectService.updateMemberRole(projectId, employeeId, role));
    }
    
    // Xóa một thành viên cụ thể ra khỏi dự án
    @DeleteMapping("/{projectId}/members/{employeeId}")
    public ResponseEntity<Void> removeMember(
            @PathVariable UUID projectId, 
            @PathVariable UUID employeeId) {
        projectService.removeMemberFromProject(projectId, employeeId);
        return ResponseEntity.noContent().build(); // Trả về 204 No Content
    }

    // Xóa hẳn dự án
    @DeleteMapping("/{projectId}")
    public ResponseEntity<Void> deleteProject(@PathVariable UUID projectId) {
        projectService.deleteProject(projectId);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/summary")
    public ResponseEntity<List<ProjectSummaryDTO>> getAllProjectSummaries() {
        return ResponseEntity.ok(projectService.getAllProjectSummaries());
    }
    
    // Nhân viên xem dự án của mình
    @GetMapping("/summary/my-projects")
    public ResponseEntity<List<ProjectSummaryDTO>> getMyProjects(@RequestParam UUID employeeId) {
        return ResponseEntity.ok(projectService.getMyProjectSummaries(employeeId));
    }
    
    // Lấy danh sách thành viên của một dự án cụ thể
    @GetMapping("/{projectId}/members")
    public ResponseEntity<List<ProjectMemberResponseDTO>> getProjectMembers(@PathVariable UUID projectId) {
    	return ResponseEntity.ok(projectService.getMembersByProjectId(projectId));
    }
}