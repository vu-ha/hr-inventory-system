package vn.edu.hust.vha.hims.modules.organization.service;

import java.util.UUID;

import vn.edu.hust.vha.hims.modules.organization.mapper.dto.request.AppointmentWithDecisionDTO;
import vn.edu.hust.vha.hims.modules.organization.mapper.dto.response.AppointmentWithDecisionResponseDTO;
import vn.edu.hust.vha.hims.modules.organization.mapper.dto.response.DepartmentDashboardDTO;
import vn.edu.hust.vha.hims.modules.organization.mapper.dto.response.PositionHoldersResponseDTO;

public interface WorkflowService {
    
    /**
     * Tạo quyết định và bổ nhiệm trong 1 transaction
     */
    AppointmentWithDecisionResponseDTO createAppointmentWithDecision(AppointmentWithDecisionDTO dto);
    
    /**
     * Lấy thông tin nhân viên hiện tại trong phòng ban
     */
    DepartmentDashboardDTO getDepartmentMembers(UUID departmentId);
    
    /**
     * Lấy danh sách người đang giữ vị trí
     */
    PositionHoldersResponseDTO getPositionHolders(UUID positionId);
}