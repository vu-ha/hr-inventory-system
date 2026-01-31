package vn.edu.hust.vha.hims.modules.organization.service.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import vn.edu.hust.vha.hims.common.entity.Employee;
import vn.edu.hust.vha.hims.common.enumeration.AppointmentStatus;
import vn.edu.hust.vha.hims.common.repository.EmployeeRepository;
import vn.edu.hust.vha.hims.modules.organization.entity.Appointment;
import vn.edu.hust.vha.hims.modules.organization.entity.Decision;
import vn.edu.hust.vha.hims.modules.organization.entity.Department;
import vn.edu.hust.vha.hims.modules.organization.entity.Position;
import vn.edu.hust.vha.hims.modules.organization.mapper.dto.request.AppointmentCreateDTO;
import vn.edu.hust.vha.hims.modules.organization.mapper.dto.request.AppointmentTerminateDTO;
import vn.edu.hust.vha.hims.modules.organization.mapper.dto.request.BatchAppointmentCreateDTO;
import vn.edu.hust.vha.hims.modules.organization.mapper.dto.response.AppointmentResponseDTO;
import vn.edu.hust.vha.hims.modules.organization.repository.AppointmentRepository;
import vn.edu.hust.vha.hims.modules.organization.repository.DecisionRepository;
import vn.edu.hust.vha.hims.modules.organization.repository.DepartmentRepository;
import vn.edu.hust.vha.hims.modules.organization.repository.PositionRepository;
import vn.edu.hust.vha.hims.modules.organization.service.AppointmentService;

@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final DecisionRepository decisionRepository;
    private final PositionRepository positionRepository;

    @Override
    @Transactional
    public AppointmentResponseDTO createAppointment(AppointmentCreateDTO dto) {

        Employee employee = employeeRepository.findById(dto.getEmployeeId())
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy nhân viên"));
        
        Department department = departmentRepository.findById(dto.getDepartmentId())
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy phòng ban"));
        
        Position position = positionRepository.findById(dto.getPositionId())
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy vị trí"));
        
        Decision decision = decisionRepository.findById(dto.getDecisionId())
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy quyết định"));
        
        if (dto.isPrimary()) {
            handlePrimaryTransfer(dto);
        } else {
            if (dto.getReplacedAppointmentId() != null) {
                // Trường hợp thay thế bổ nhiệm cũ
                Appointment oldApp = appointmentRepository.findById(dto.getReplacedAppointmentId())
                        .orElseThrow(() -> new RuntimeException("Không tìm thấy bổ nhiệm cũ để thay thế"));
                
                if (dto.getSalaryPercentage() == null) {
                    dto.setSalaryPercentage(oldApp.getSalaryPercentage());
                }

                oldApp.setStatus(AppointmentStatus.EXPIRED);
                oldApp.setEndDate(dto.getStartDate().minusDays(1));
                appointmentRepository.save(oldApp);

            } else {
                // Trường hợp bổ nhiệm thêm (kiêm nhiệm)
                handleSecondaryAppointment(dto);
            }
        }
        
        Appointment newApp = Appointment.builder()
                .employee(employee)
                .department(department)
                .position(position)
                .decision(decision)
                .isPrimary(dto.isPrimary())
                .salaryPercentage(dto.getSalaryPercentage())
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .status(AppointmentStatus.ACTIVE)
                .build();

        Appointment savedApp = appointmentRepository.save(newApp);
        
        validateTotalPercentage(dto.getEmployeeId());
        
        return mapToResponse(savedApp);
    }

    @Override
    @Transactional
    public List<AppointmentResponseDTO> createBatchAppointments(BatchAppointmentCreateDTO dto) {
        // Validate decision exists
        Decision decision = decisionRepository.findById(dto.getDecisionId())
                .orElseThrow(() -> new EntityNotFoundException("Decision not found"));
        
        List<AppointmentResponseDTO> results = new ArrayList<>();
        
        for (AppointmentCreateDTO appointmentDTO : dto.getAppointments()) {
            // Set decisionId cho mỗi appointment
            appointmentDTO.setDecisionId(dto.getDecisionId());
            
            // Tạo từng appointment
            AppointmentResponseDTO created = createAppointment(appointmentDTO);
            results.add(created);
        }
        
        return results;
    }

    @Override
    @Transactional(readOnly = true)
    public AppointmentResponseDTO getAppointment(UUID appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new EntityNotFoundException("Appointment not found with id: " + appointmentId));
        
        return mapToResponse(appointment);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AppointmentResponseDTO> getAppointments(
            UUID employeeId,
            UUID departmentId,
            UUID positionId,
            AppointmentStatus status,
            Boolean isPrimary,
            Pageable pageable) {
        
        Specification<Appointment> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            
            if (employeeId != null) {
                predicates.add(cb.equal(root.get("employee").get("id"), employeeId));
            }
            
            if (departmentId != null) {
                predicates.add(cb.equal(root.get("department").get("id"), departmentId));
            }
            
            if (positionId != null) {
                predicates.add(cb.equal(root.get("position").get("id"), positionId));
            }
            
            if (status != null) {
                predicates.add(cb.equal(root.get("status"), status));
            }
            
            if (isPrimary != null) {
                predicates.add(cb.equal(root.get("isPrimary"), isPrimary));
            }
            
            return cb.and(predicates.toArray(new Predicate[0]));
        };
        
        return appointmentRepository.findAll(spec, pageable)
                .map(this::mapToResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AppointmentResponseDTO> getEmployeeAppointmentHistory(UUID employeeId) {
        // Validate employee exists
        employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found"));
        
        // Sử dụng query method trực tiếp thay vì Specification
        List<Appointment> appointments = appointmentRepository
                .findByEmployeeIdOrderByStartDateDesc(employeeId);
        
        return appointments.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<AppointmentResponseDTO> getEmployeeActiveAppointments(UUID employeeId) {
        // Validate employee exists
        employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found"));
        
        List<Appointment> activeAppointments = appointmentRepository
                .findByEmployeeIdAndStatus(employeeId, AppointmentStatus.ACTIVE);
        
        return activeAppointments.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public AppointmentResponseDTO terminateAppointment(UUID appointmentId, AppointmentTerminateDTO dto) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new EntityNotFoundException("Appointment not found"));
        
        // Validate appointment is active
        if (appointment.getStatus() != AppointmentStatus.ACTIVE) {
            throw new RuntimeException("Can only terminate ACTIVE appointments");
        }
        
        // Validate end date
        if (dto.getEndDate().isBefore(appointment.getStartDate())) {
            throw new RuntimeException("End date cannot be before start date");
        }
        
        if (dto.getEndDate().isBefore(LocalDate.now())) {
            throw new RuntimeException("End date cannot be in the past");
        }
        
        BigDecimal returnedPercent = appointment.getSalaryPercentage();
        UUID empId = appointment.getEmployee().getId();

        appointment.setStatus(AppointmentStatus.EXPIRED);
        appointment.setEndDate(dto.getEndDate());
        appointmentRepository.saveAndFlush(appointment);

        if (!appointment.isPrimary()) {
            // TRẢ LƯƠNG VỀ CHO PRIMARY
            Appointment primaryApp = appointmentRepository.findPrimaryActive(empId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy vị trí chính để hoàn trả lương"));
            
            primaryApp.setSalaryPercentage(primaryApp.getSalaryPercentage().add(returnedPercent));
            appointmentRepository.save(primaryApp);
        } else {
            // Nếu kết thúc Primary, đóng hết Secondary (như bạn đã làm)
            handlePrimaryTermination(empId, dto.getEndDate());
        }
        
        // Cuối cùng mới validate, lúc này tổng sẽ là 100% nên sẽ PASS
        validateTotalPercentage(empId); 
        return mapToResponse(appointment);
    }

    // Private helper methods
    
    private void handlePrimaryTransfer(AppointmentCreateDTO dto) {
        List<Appointment> activeApps = appointmentRepository
                .findByEmployeeIdAndStatus(dto.getEmployeeId(), AppointmentStatus.ACTIVE);

        for (Appointment app : activeApps) {
            app.setStatus(AppointmentStatus.EXPIRED);
            app.setEndDate(dto.getStartDate().minusDays(1));
        }
        
        appointmentRepository.saveAll(activeApps);
        
        if (dto.getSalaryPercentage() == null) {
            dto.setSalaryPercentage(new BigDecimal("100.00"));
        }
    }

    private void handleSecondaryAppointment(AppointmentCreateDTO dto) {
        Appointment primaryApp = appointmentRepository
                .findPrimaryActive(dto.getEmployeeId())
                .orElseThrow(() -> new RuntimeException("Nhân viên phải có một vị trí chính (Primary) trước khi bổ nhiệm kiêm nhiệm"));

        BigDecimal currentPrimaryPercent = primaryApp.getSalaryPercentage();
        BigDecimal newSecondaryPercent = dto.getSalaryPercentage();

        if (newSecondaryPercent.compareTo(currentPrimaryPercent) >= 0) {
            throw new RuntimeException("Tỷ lệ lương kiêm nhiệm phải nhỏ hơn tỷ lệ lương của vị trí chính hiện tại");
        }

        primaryApp.setSalaryPercentage(currentPrimaryPercent.subtract(newSecondaryPercent));
        appointmentRepository.save(primaryApp);
    }
    
    private void handlePrimaryTermination(UUID employeeId, LocalDate terminationDate) {
        // Kết thúc tất cả các secondary appointments
        List<Appointment> secondaryApps = appointmentRepository.findAll(
            (root, query, cb) -> cb.and(
                cb.equal(root.get("employee").get("id"), employeeId),
                cb.equal(root.get("status"), AppointmentStatus.ACTIVE),
                cb.equal(root.get("isPrimary"), false)
            )
        );
        
        for (Appointment app : secondaryApps) {
            app.setStatus(AppointmentStatus.EXPIRED);
            app.setEndDate(terminationDate);
        }
        
        appointmentRepository.saveAll(secondaryApps);
    }
    
    private AppointmentResponseDTO mapToResponse(Appointment app) {
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
    
    private void validateTotalPercentage(UUID employeeId) {
        List<Appointment> actives = appointmentRepository.findByEmployeeIdAndStatus(employeeId, AppointmentStatus.ACTIVE);
        BigDecimal total = actives.stream()
                .map(Appointment::getSalaryPercentage)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        if (total.compareTo(new BigDecimal("100.00")) != 0) {
            throw new RuntimeException("Lỗi logic: Tổng tỷ lệ lương không bằng 100% (Hiện tại: " + total + "%)");
        }
    }
}