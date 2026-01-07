package vn.edu.hust.vha.hims.modules.organization.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import vn.edu.hust.vha.hims.common.entity.Employee;
import vn.edu.hust.vha.hims.common.enumeration.AppointmentStatus;
import vn.edu.hust.vha.hims.common.repository.EmployeeRepository;
import vn.edu.hust.vha.hims.modules.organization.dto.request.AppointmentCreateDTO;
import vn.edu.hust.vha.hims.modules.organization.dto.response.AppointmentResponseDTO;
import vn.edu.hust.vha.hims.modules.organization.entity.Appointment;
import vn.edu.hust.vha.hims.modules.organization.entity.Decision;
import vn.edu.hust.vha.hims.modules.organization.entity.Department;
import vn.edu.hust.vha.hims.modules.organization.entity.Position;
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
    //private final AllowanceRepository allowanceRepository; 
    @Override
    @Transactional
    public AppointmentResponseDTO createAppointment(AppointmentCreateDTO dto) {

        Employee employee = employeeRepository.findById(dto.getEmployeeId())
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy nhân viên"));
        
        Department department = departmentRepository.findById(dto.getDepartmentId())
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy phòng ban"));
        
        Position position = positionRepository.findById(dto.getPositionId())
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy phòng ban"));
        
        Decision decision = decisionRepository.findById(dto.getDecisionId())
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy quyết định"));
        
        if (dto.isPrimary()) {
            handlePrimaryTransfer(dto);
        } else {
        	if (dto.getReplacedAppointmentId() != null) {
                // A. TRƯỜNG HỢP THAY THẾ 1 CÁI CỤ THỂ
                Appointment oldApp = appointmentRepository.findById(dto.getReplacedAppointmentId())
                        .orElseThrow(() -> new RuntimeException("Không tìm thấy bổ nhiệm cũ để thay thế"));
                
                // Lấy lại % từ cái cũ để gán cho cái mới
                if (dto.getSalaryPercentage() == null) {
                    dto.setSalaryPercentage(oldApp.getSalaryPercentage());
                }

                // Đóng cái cũ
                oldApp.setStatus(AppointmentStatus.EXPIRED);
                oldApp.setEndDate(dto.getStartDate().minusDays(1));
                appointmentRepository.save(oldApp);
                //closeRelatedAllowances(oldApp.getId(), oldApp.getEndDate());

            } else {
                // B. TRƯỜNG HỢP BỔ NHIỆM THÊM (Cộng dồn/Chia nhỏ thêm)
                handleSecondaryAppointment(dto); // Logic trừ % từ Primary như đã viết
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

    private void handlePrimaryTransfer(AppointmentCreateDTO dto) {
        // Tìm tất cả các bổ nhiệm (cả chính và phụ) đang ACTIVE để đóng lại
        List<Appointment> activeApps = appointmentRepository
                .findByEmployeeIdAndStatus(dto.getEmployeeId(), AppointmentStatus.ACTIVE);

        for (Appointment app : activeApps) {
        	app.setStatus(AppointmentStatus.EXPIRED);
            app.setEndDate(dto.getStartDate().minusDays(1)); // Kết thúc trước ngày bắt đầu bổ nhiệm mới
            
            /*
            // Nếu có phụ cấp gắn với bổ nhiệm này, cũng cần đóng phụ cấp đó
            closeRelatedAllowances(app.getId(), dto.getStartDate().minusDays(1));
            */
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
    
    private AppointmentResponseDTO mapToResponse(Appointment app) {
    	return AppointmentResponseDTO.builder()
    			.appointmentId(app.getId())
    			.employeeId(app.getEmployee().getId())
    			.employeeFullName(app.getEmployee().getFirstName() + " "  + app.getEmployee().getLastName())
    			.decisionId(app.getDecision().getId())
    			.positionId(app.getPosition().getId())
    			.positionName(app.getPosition().getName())
    			.departmentId(app.getDepartment().getId())
    			.departmentName(app.getDepartment().getName())
    			.isPrimary(app.isPrimary())
    			.salaryPercentage(app.getSalaryPercentage())
    			.startDate(app.getStartDate())
    			.endDate(app.getEndDate())
    			.createdAt(app.getCreatedAt())
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

    /*
    private void closeRelatedAllowances(UUID appointmentId, LocalDate expireDate) {
        List<EmployeeAllowance> allowances = allowanceRepository.findByAppointmentId(appointmentId);
        for (EmployeeAllowance allowance : allowances) {
            if (allowance.getExpirationDate() == null) {
                allowance.setExpirationDate(expireDate);
            }
        }
        allowanceRepository.saveAll(allowances);
    }
    */
    
    /**
     * Hàm hỗ trợ đóng tất cả các phụ cấp gắn liền với một bổ nhiệm cụ thể.
     * Khi bổ nhiệm hết hạn, các phụ cấp đi kèm cũng phải hết hạn.
    private void closeRelatedAllowances(UUID appointmentId, LocalDate expireDate) {
        // 1. Tìm tất cả phụ cấp đang gắn với Appointment này và vẫn còn hiệu lực (expirationDate là null)
        List<EmployeeAllowance> allowances = allowanceRepository.findByAppointmentId(appointmentId);
        
        if (allowances != null && !allowances.isEmpty()) {
            for (EmployeeAllowance allowance : allowances) {
                // Chỉ cập nhật những phụ cấp chưa có ngày hết hạn hoặc ngày hết hạn sau ngày đóng bổ nhiệm
                if (allowance.getExpirationDate() == null || allowance.getExpirationDate().isAfter(expireDate)) {
                    allowance.setExpirationDate(expireDate);
                    
                    // (Tùy chọn) Thêm note để sau này dễ truy vết
                    String currentNote = allowance.getNote() != null ? allowance.getNote() : "";
                    allowance.setNote(currentNote + " [Hệ thống: Tự động đóng do Bổ nhiệm gắn kèm kết thúc]");
                }
            }
            
            // 2. Lưu hàng loạt thay đổi
            allowanceRepository.saveAll(allowances);
            log.info("Đã đóng {} phụ cấp đi kèm với bổ nhiệm ID: {}", allowances.size(), appointmentId);
        }
    }
    */
}