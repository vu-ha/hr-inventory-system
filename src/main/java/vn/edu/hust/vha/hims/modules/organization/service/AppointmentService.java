package vn.edu.hust.vha.hims.modules.organization.service;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import vn.edu.hust.vha.hims.common.enumeration.AppointmentStatus;
import vn.edu.hust.vha.hims.modules.organization.mapper.dto.request.AppointmentCreateDTO;
import vn.edu.hust.vha.hims.modules.organization.mapper.dto.request.AppointmentTerminateDTO;
import vn.edu.hust.vha.hims.modules.organization.mapper.dto.request.BatchAppointmentCreateDTO;
import vn.edu.hust.vha.hims.modules.organization.mapper.dto.response.AppointmentResponseDTO;

public interface AppointmentService {
    
    /**
     * Tạo bổ nhiệm mới
     */
    AppointmentResponseDTO createAppointment(AppointmentCreateDTO dto);
    
    /**
     * Tạo nhiều bổ nhiệm cùng lúc từ 1 quyết định
     */
    List<AppointmentResponseDTO> createBatchAppointments(BatchAppointmentCreateDTO dto);
    
    /**
     * Lấy chi tiết bổ nhiệm
     */
    AppointmentResponseDTO getAppointment(UUID appointmentId);
    
    /**
     * Lấy danh sách bổ nhiệm với filter và phân trang
     */
    Page<AppointmentResponseDTO> getAppointments(
            UUID employeeId,
            UUID departmentId,
            UUID positionId,
            AppointmentStatus status,
            Boolean isPrimary,
            Pageable pageable);
    
    /**
     * Lấy lịch sử bổ nhiệm của nhân viên (sắp xếp theo thời gian)
     */
    List<AppointmentResponseDTO> getEmployeeAppointmentHistory(UUID employeeId);
    
    /**
     * Lấy các bổ nhiệm đang active của nhân viên
     */
    List<AppointmentResponseDTO> getEmployeeActiveAppointments(UUID employeeId);
    
    /**
     * Kết thúc bổ nhiệm sớm
     */
    AppointmentResponseDTO terminateAppointment(UUID appointmentId, AppointmentTerminateDTO dto);
}