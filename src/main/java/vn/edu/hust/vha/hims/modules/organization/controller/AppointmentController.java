package vn.edu.hust.vha.hims.modules.organization.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import vn.edu.hust.vha.hims.common.enumeration.AppointmentStatus;
import vn.edu.hust.vha.hims.modules.organization.mapper.dto.request.AppointmentCreateDTO;
import vn.edu.hust.vha.hims.modules.organization.mapper.dto.request.AppointmentTerminateDTO;
import vn.edu.hust.vha.hims.modules.organization.mapper.dto.request.BatchAppointmentCreateDTO;
import vn.edu.hust.vha.hims.modules.organization.mapper.dto.response.AppointmentResponseDTO;
import vn.edu.hust.vha.hims.modules.organization.service.AppointmentService;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AppointmentController {
    
    private final AppointmentService appointmentService;

    /**
     * 6. POST /api/decisions/{decisionId}/appointments - Tạo bổ nhiệm từ quyết định
     */
    @PostMapping("/decisions/{decisionId}/appointments")
    public ResponseEntity<AppointmentResponseDTO> createAppointment(
            @PathVariable UUID decisionId,
            @Valid @RequestBody AppointmentCreateDTO dto) {
        dto.setDecisionId(decisionId); // Đảm bảo decisionId từ path
        AppointmentResponseDTO response = appointmentService.createAppointment(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * 7. POST /api/appointments/batch - Tạo nhiều bổ nhiệm cùng lúc
     */
    @PostMapping("/appointments/batch")
    public ResponseEntity<List<AppointmentResponseDTO>> createBatchAppointments(
            @Valid @RequestBody BatchAppointmentCreateDTO dto) {
        List<AppointmentResponseDTO> response = appointmentService.createBatchAppointments(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * 8. GET /api/appointments/{id} - Xem chi tiết bổ nhiệm
     */
    @GetMapping("/appointments/{id}")
    public ResponseEntity<AppointmentResponseDTO> getAppointment(@PathVariable UUID id) {
        AppointmentResponseDTO response = appointmentService.getAppointment(id);
        return ResponseEntity.ok(response);
    }

    /**
     * 9. GET /api/appointments - Lấy danh sách bổ nhiệm (phân trang, filter)
     */
    @GetMapping("/appointments")
    public ResponseEntity<Page<AppointmentResponseDTO>> getAppointments(
            @RequestParam(required = false) UUID employeeId,
            @RequestParam(required = false) UUID departmentId,
            @RequestParam(required = false) UUID positionId,
            @RequestParam(required = false) AppointmentStatus status,
            @RequestParam(required = false) Boolean isPrimary,
            Pageable pageable) {
        Page<AppointmentResponseDTO> response = appointmentService.getAppointments(
                employeeId, departmentId, positionId, status, isPrimary, pageable);
        return ResponseEntity.ok(response);
    }

    /**
     * 10. GET /api/employees/{employeeId}/appointments - Lịch sử bổ nhiệm của nhân viên
     */
    @GetMapping("/employees/{employeeId}/appointments")
    public ResponseEntity<List<AppointmentResponseDTO>> getEmployeeAppointmentHistory(
            @PathVariable UUID employeeId) {
        List<AppointmentResponseDTO> response = appointmentService.getEmployeeAppointmentHistory(employeeId);
        return ResponseEntity.ok(response);
    }

    /**
     * 11. GET /api/employees/{employeeId}/appointments/active - Bổ nhiệm đang active
     */
    @GetMapping("/employees/{employeeId}/appointments/active")
    public ResponseEntity<List<AppointmentResponseDTO>> getEmployeeActiveAppointments(
            @PathVariable UUID employeeId) {
        List<AppointmentResponseDTO> response = appointmentService.getEmployeeActiveAppointments(employeeId);
        return ResponseEntity.ok(response);
    }

    /**
     * 12. PUT /api/appointments/{id}/terminate - Kết thúc bổ nhiệm sớm
     */
    @PutMapping("/appointments/{id}/terminate")
    public ResponseEntity<AppointmentResponseDTO> terminateAppointment(
            @PathVariable UUID id,
            @Valid @RequestBody AppointmentTerminateDTO dto) {
        AppointmentResponseDTO response = appointmentService.terminateAppointment(id, dto);
        return ResponseEntity.ok(response);
    }
}