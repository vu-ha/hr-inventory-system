package vn.edu.hust.vha.hims.modules.organization.controller;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import vn.edu.hust.vha.hims.modules.organization.mapper.dto.request.AppointmentWithDecisionDTO;
import vn.edu.hust.vha.hims.modules.organization.mapper.dto.response.AppointmentWithDecisionResponseDTO;
import vn.edu.hust.vha.hims.modules.organization.mapper.dto.response.DepartmentDashboardDTO;
import vn.edu.hust.vha.hims.modules.organization.mapper.dto.response.PositionHoldersResponseDTO;
import vn.edu.hust.vha.hims.modules.organization.service.WorkflowService;

@RestController
@RequestMapping("/api/workflows")
@RequiredArgsConstructor
public class WorkflowController {
    
    private final WorkflowService workflowService;

    /**
     * 13. POST /api/workflows/appointment-with-decision 
     * Tạo quyết định + bổ nhiệm trong 1 transaction
     */
    @PostMapping("/appointment-with-decision")
    public ResponseEntity<AppointmentWithDecisionResponseDTO> createAppointmentWithDecision(
            @Valid @RequestBody AppointmentWithDecisionDTO dto) {
        AppointmentWithDecisionResponseDTO response = 
                workflowService.createAppointmentWithDecision(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * 14. GET /api/departments/{departmentId}/members 
     * Xem nhân viên hiện tại trong phòng ban
     */
    @GetMapping("/departments/{departmentId}/members")
    public ResponseEntity<DepartmentDashboardDTO> getDepartmentMembers(
            @PathVariable UUID departmentId) {
        DepartmentDashboardDTO response = workflowService.getDepartmentMembers(departmentId);
        return ResponseEntity.ok(response);
    }

    /**
     * 15. GET /api/positions/{positionId}/holders 
     * Xem ai đang giữ vị trí này
     */
    @GetMapping("/positions/{positionId}/holders")
    public ResponseEntity<PositionHoldersResponseDTO> getPositionHolders(
            @PathVariable UUID positionId) {
        PositionHoldersResponseDTO response = workflowService.getPositionHolders(positionId);
        return ResponseEntity.ok(response);
    }
}