package vn.edu.hust.vha.hims.modules.organization.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import vn.edu.hust.vha.hims.common.entity.Employee;
import vn.edu.hust.vha.hims.common.enumeration.DecisionType;
import vn.edu.hust.vha.hims.common.repository.EmployeeRepository;
import vn.edu.hust.vha.hims.modules.organization.entity.Decision;
import vn.edu.hust.vha.hims.modules.organization.mapper.dto.request.DecisionCreateDTO;
import vn.edu.hust.vha.hims.modules.organization.mapper.dto.response.DecisionResponseDTO;
import vn.edu.hust.vha.hims.modules.organization.repository.DecisionRepository;
import vn.edu.hust.vha.hims.modules.organization.repository.PositionRepository;
import vn.edu.hust.vha.hims.modules.organization.service.DecisionService;

@Service
@RequiredArgsConstructor
public class DecisionServiceImpl implements DecisionService{
	private final DecisionRepository decisionRepository; 
	private final EmployeeRepository employeeRepository;
	private final PositionRepository positionRepository;
	
    @Override
    @Transactional
    public DecisionResponseDTO createDecision(DecisionCreateDTO dto) {
		
    	Employee employee = employeeRepository.findById(dto.getEmployeeId())
    		.orElseThrow(() -> new RuntimeException("Employee not found with id: " + dto.getEmployeeId()));
    	
    	Employee signer = employeeRepository.findById(dto.getSignerId())
    	        .orElseThrow(() -> new RuntimeException("Signer (Employee) not found"));
    	
    	Integer employeeLevel = positionRepository.findHighestLevelByEmployeeId(employee.getId());
    	Integer signerLevel = positionRepository.findHighestLevelByEmployeeId(signer.getId());
    	
    	if (signerLevel == null || employeeLevel == null) {
    	    throw new RuntimeException("Nhân viên hoặc người ký chưa được bổ nhiệm vị trí chính thức");
    	}

    	if (signerLevel >= employeeLevel) {
    	    throw new RuntimeException("Người ký phải có cấp bậc cao hơn người nhận quyết định (Level " + signerLevel + " không thể ký cho Level " + employeeLevel + ")");
    	}
    	
    	Decision decision = Decision.builder()
    		.employee(employee)
    		.signer(signer)
    		.decisionType(DecisionType.APPOINT)
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
    
    
    
}
