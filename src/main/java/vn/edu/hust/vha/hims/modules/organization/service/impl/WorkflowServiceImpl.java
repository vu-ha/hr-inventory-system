package vn.edu.hust.vha.hims.modules.organization.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import vn.edu.hust.vha.hims.common.enumeration.AppointmentStatus;
import vn.edu.hust.vha.hims.common.mapper.dto.response.EmployeeSummaryDTO;
import vn.edu.hust.vha.hims.modules.organization.entity.Appointment;
import vn.edu.hust.vha.hims.modules.organization.entity.Department;
import vn.edu.hust.vha.hims.modules.organization.entity.Position;
import vn.edu.hust.vha.hims.modules.organization.mapper.dto.request.AppointmentCreateDTO;
import vn.edu.hust.vha.hims.modules.organization.mapper.dto.request.AppointmentWithDecisionDTO;
import vn.edu.hust.vha.hims.modules.organization.mapper.dto.response.AppointmentResponseDTO;
import vn.edu.hust.vha.hims.modules.organization.mapper.dto.response.AppointmentWithDecisionResponseDTO;
import vn.edu.hust.vha.hims.modules.organization.mapper.dto.response.DecisionResponseDTO;
import vn.edu.hust.vha.hims.modules.organization.mapper.dto.response.DepartmentDashboardDTO;
import vn.edu.hust.vha.hims.modules.organization.mapper.dto.response.PositionHoldersResponseDTO;
import vn.edu.hust.vha.hims.modules.organization.repository.AppointmentRepository;
import vn.edu.hust.vha.hims.modules.organization.repository.DepartmentRepository;
import vn.edu.hust.vha.hims.modules.organization.repository.PositionRepository;
import vn.edu.hust.vha.hims.modules.organization.service.AppointmentService;
import vn.edu.hust.vha.hims.modules.organization.service.DecisionService;
import vn.edu.hust.vha.hims.modules.organization.service.WorkflowService;

@Service
@RequiredArgsConstructor
public class WorkflowServiceImpl implements WorkflowService {
    
    private final DecisionService decisionService;
    private final AppointmentService appointmentService;
    private final DepartmentRepository departmentRepository;
    private final PositionRepository positionRepository;
    private final AppointmentRepository appointmentRepository;

    @Override
    @Transactional
    public AppointmentWithDecisionResponseDTO createAppointmentWithDecision(AppointmentWithDecisionDTO dto) {
        // 1. Tạo quyết định trước
        DecisionResponseDTO decision = decisionService.createDecision(dto.getDecision());
        
        // 2. Tạo các bổ nhiệm, set decisionId từ quyết định vừa tạo
        List<AppointmentResponseDTO> appointments = new ArrayList<>();
        for (AppointmentCreateDTO appointmentDTO : dto.getAppointments()) {
            appointmentDTO.setDecisionId(decision.getDecisionId());
            AppointmentResponseDTO appointment = appointmentService.createAppointment(appointmentDTO);
            appointments.add(appointment);
        }
        
        // 3. Trả về kết quả
        return AppointmentWithDecisionResponseDTO.builder()
                .decision(decision)
                .appointments(appointments)
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public DepartmentDashboardDTO getDepartmentMembers(UUID departmentId) {
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new EntityNotFoundException("Department not found with id: " + departmentId));

        // Lấy tất cả active appointments của department này
        Specification<Appointment> spec = (root, query, cb) -> cb.and(
            cb.equal(root.get("department").get("id"), departmentId),
            cb.equal(root.get("status"), AppointmentStatus.ACTIVE),
            cb.equal(root.get("isPrimary"), true)
        );
        
        List<Appointment> activeAppointments = appointmentRepository.findAll(spec);

        // Map sang EmployeeSummaryDTO
        List<EmployeeSummaryDTO> employees = activeAppointments.stream()
                .map(this::mapToEmployeeSummary)
                .collect(Collectors.toList());

        String managerName = department.getManager() != null
                ? department.getManager().getFirstName() + " " + department.getManager().getLastName()
                : "Chưa có quản lý";

        return new DepartmentDashboardDTO(
                department.getId(),
                department.getName(),
                managerName,
                employees
        );
    }

    // Helper method
    private EmployeeSummaryDTO mapToEmployeeSummary(Appointment app) {
        return new EmployeeSummaryDTO(
                app.getEmployee().getId(),
                app.getEmployee().getFirstName() + " " + app.getEmployee().getLastName(),
                app.getEmployee().getGender(),
                app.getEmployee().getEmail(),
                app.getEmployee().getPhoneNumber(),
                app.getPosition().getManagementLevel(),
                app.getPosition().getName()
        );
    }

    @Override
    @Transactional(readOnly = true)
    public PositionHoldersResponseDTO getPositionHolders(UUID positionId) {
        Position position = positionRepository.findById(positionId)
                .orElseThrow(() -> new EntityNotFoundException("Position not found with id: " + positionId));
        
        // Lấy tất cả active appointments cho vị trí này
        List<Appointment> activeHolders = appointmentRepository.findAll(
            (root, query, cb) -> cb.and(
                cb.equal(root.get("position").get("id"), positionId),
                cb.equal(root.get("status"), AppointmentStatus.ACTIVE)
            )
        );
        
        List<AppointmentResponseDTO> holders = activeHolders.stream()
                .map(this::mapAppointmentToResponse)
                .collect(Collectors.toList());
        
        return PositionHoldersResponseDTO.builder()
                .positionId(position.getId())
                .positionName(position.getName())
                .description(position.getDescription())
                .jobGradeName(position.getJobGrade().getName())
                .managementLevel(position.getManagementLevel())
                .totalHolders(holders.size())
                .currentHolders(holders)
                .build();
    }
    
    // Helper method
    private AppointmentResponseDTO mapAppointmentToResponse(Appointment app) {
        return AppointmentResponseDTO.builder()
                .appointmentId(app.getId())
                .employeeId(app.getEmployee().getId())
                .employeeFullName(app.getEmployee().getFirstName() + " " + app.getEmployee().getLastName())
                .decisionId(app.getDecision().getId())
                .positionId(app.getPosition().getId())
                .positionName(app.getPosition().getName())
                .departmentId(app.getDepartment().getId())
                .departmentName(app.getDepartment().getName())
                .status(app.getStatus())
                .isPrimary(app.isPrimary())
                .salaryPercentage(app.getSalaryPercentage())
                .startDate(app.getStartDate())
                .endDate(app.getEndDate())
                .createdAt(app.getCreatedAt())
                .updatedAt(app.getUpdatedAt())
                .createdByUUID(app.getCreatedBy())
                .build();
    }
}