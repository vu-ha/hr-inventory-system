package vn.edu.hust.vha.hims.modules.organization.service.impl;

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
import vn.edu.hust.vha.hims.common.enumeration.DecisionType;
import vn.edu.hust.vha.hims.common.repository.EmployeeRepository;
import vn.edu.hust.vha.hims.modules.organization.entity.Appointment;
import vn.edu.hust.vha.hims.modules.organization.entity.Decision;
import vn.edu.hust.vha.hims.modules.organization.mapper.dto.request.DecisionCreateDTO;
import vn.edu.hust.vha.hims.modules.organization.mapper.dto.response.AppointmentResponseDTO;
import vn.edu.hust.vha.hims.modules.organization.mapper.dto.response.DecisionDetailResponseDTO;
import vn.edu.hust.vha.hims.modules.organization.mapper.dto.response.DecisionResponseDTO;
import vn.edu.hust.vha.hims.modules.organization.repository.DecisionRepository;
import vn.edu.hust.vha.hims.modules.organization.repository.PositionRepository;
import vn.edu.hust.vha.hims.modules.organization.service.DecisionService;

@Service
@RequiredArgsConstructor
public class DecisionServiceImpl implements DecisionService {
    
    private final DecisionRepository decisionRepository;
    private final EmployeeRepository employeeRepository;
    private final PositionRepository positionRepository;

    @Override
    @Transactional
    public DecisionResponseDTO createDecision(DecisionCreateDTO dto) {
        
        Employee employee = employeeRepository.findById(dto.getEmployeeId())
                .orElseThrow(() -> new EntityNotFoundException("Employee not found with id: " + dto.getEmployeeId()));
        
        Employee signer = employeeRepository.findById(dto.getSignerId())
                .orElseThrow(() -> new EntityNotFoundException("Signer (Employee) not found with id: " + dto.getSignerId()));
        
        /*
        // Validate signer level
        Integer employeeLevel = positionRepository.findHighestLevelByEmployeeId(employee.getId());
        Integer signerLevel = positionRepository.findHighestLevelByEmployeeId(signer.getId());
        if (signerLevel == null || employeeLevel == null) {
            throw new RuntimeException("Nhân viên hoặc người ký chưa được bổ nhiệm vị trí chính thức");
        }

        if (signerLevel >= employeeLevel) {
            throw new RuntimeException("Người ký phải có cấp bậc cao hơn người nhận quyết định (Level " 
                    + signerLevel + " không thể ký cho Level " + employeeLevel + ")");
        }
        */
        
        Decision decision = Decision.builder()
                .employee(employee)
                .signer(signer)
                .decisionType(dto.getDecisionType())
                .decisionNumber(dto.getDecisionNumber())
                .content(dto.getContent())
                .decisionURL(dto.getDecisionURL())
                .signedDate(dto.getSignedDate())
                .effectiveDate(dto.getEffectiveDate())
                .expiredDate(dto.getExpiredDate())
                .build();
        
        Decision saved = decisionRepository.save(decision);
        return mapToResponseDTO(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public DecisionDetailResponseDTO getDecisionDetail(UUID decisionId) {
        Decision decision = decisionRepository.findById(decisionId)
                .orElseThrow(() -> new EntityNotFoundException("Decision not found with id: " + decisionId));
        
        // Map decision
        DecisionResponseDTO decisionDTO = mapToResponseDTO(decision);
        
        // Map appointments
        List<AppointmentResponseDTO> appointmentDTOs = decision.getAppointments().stream()
                .map(this::mapAppointmentToResponse)
                .collect(Collectors.toList());
        
        return DecisionDetailResponseDTO.builder()
                .decision(decisionDTO)
                .appointments(appointmentDTOs)
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DecisionResponseDTO> getDecisions(
            UUID employeeId, 
            DecisionType decisionType, 
            LocalDate fromDate, 
            LocalDate toDate, 
            Pageable pageable) {
        
        Specification<Decision> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            
            if (employeeId != null) {
                predicates.add(cb.equal(root.get("employee").get("id"), employeeId));
            }
            
            if (decisionType != null) {
                predicates.add(cb.equal(root.get("decisionType"), decisionType));
            }
            
            if (fromDate != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("effectiveDate"), fromDate));
            }
            
            if (toDate != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("effectiveDate"), toDate));
            }
            
            return cb.and(predicates.toArray(new Predicate[0]));
        };
        
        return decisionRepository.findAll(spec, pageable)
                .map(this::mapToResponseDTO);
    }

    @Override
    @Transactional
    public DecisionResponseDTO updateDecision(UUID decisionId, DecisionCreateDTO dto) {
        Decision decision = decisionRepository.findById(decisionId)
                .orElseThrow(() -> new EntityNotFoundException("Decision not found with id: " + decisionId));
        
        // Kiểm tra quyết định đã có hiệu lực chưa
        if (decision.getEffectiveDate().isBefore(LocalDate.now())) {
            throw new RuntimeException("Cannot update decision that has already taken effect");
        }
        
        // Kiểm tra đã có bổ nhiệm nào chưa
        if (!decision.getAppointments().isEmpty()) {
            throw new RuntimeException("Cannot update decision that already has appointments");
        }
        
        // Update fields
        Employee employee = employeeRepository.findById(dto.getEmployeeId())
                .orElseThrow(() -> new EntityNotFoundException("Employee not found"));
        
        Employee signer = employeeRepository.findById(dto.getSignerId())
                .orElseThrow(() -> new EntityNotFoundException("Signer not found"));
        
        decision.setEmployee(employee);
        decision.setSigner(signer);
        decision.setDecisionType(dto.getDecisionType());
        decision.setDecisionNumber(dto.getDecisionNumber());
        decision.setContent(dto.getContent());
        decision.setDecisionURL(dto.getDecisionURL());
        decision.setSignedDate(dto.getSignedDate());
        decision.setEffectiveDate(dto.getEffectiveDate());
        decision.setExpiredDate(dto.getExpiredDate());
        
        Decision updated = decisionRepository.save(decision);
        return mapToResponseDTO(updated);
    }

    @Override
    @Transactional
    public void deleteDecision(UUID decisionId) {
        Decision decision = decisionRepository.findById(decisionId)
                .orElseThrow(() -> new EntityNotFoundException("Decision not found with id: " + decisionId));
        
        // Kiểm tra đã có bổ nhiệm nào chưa
        if (!decision.getAppointments().isEmpty()) {
            throw new RuntimeException("Cannot delete decision that has appointments. Found " 
                    + decision.getAppointments().size() + " appointment(s)");
        }
        
        decisionRepository.delete(decision);
    }

    // Helper methods
    private DecisionResponseDTO mapToResponseDTO(Decision decision) {
        return DecisionResponseDTO.builder()
                .decisionId(decision.getId())
                .decisionNumber(decision.getDecisionNumber())
                .decisionType(decision.getDecisionType())
                .content(decision.getContent())
                .decisionURL(decision.getDecisionURL())
                .employeeFullName(decision.getEmployee().getFirstName() + " " + decision.getEmployee().getLastName())
                .employeeId(decision.getEmployee().getId())
                .signerFullName(decision.getSigner().getFirstName() + " " + decision.getSigner().getLastName())
                .signerId(decision.getSigner().getId())
                .signedDate(decision.getSignedDate())
                .effectiveDate(decision.getEffectiveDate())
                .expiredDate(decision.getExpiredDate())
                .createdAt(decision.getCreatedAt())
                .updatedAt(decision.getUpdatedAt())
                .createdByUUID(decision.getCreatedBy())
                .build();
    }
    
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