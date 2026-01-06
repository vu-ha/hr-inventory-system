package vn.edu.hust.vha.hims.modules.contract.service.impl;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import vn.edu.hust.vha.hims.common.entity.Employee;
import vn.edu.hust.vha.hims.common.enumeration.ContractStatus;
import vn.edu.hust.vha.hims.common.repository.EmployeeRepository;
import vn.edu.hust.vha.hims.modules.contract.dto.ContractCreateDTO;
import vn.edu.hust.vha.hims.modules.contract.dto.ContractResponseDTO;
import vn.edu.hust.vha.hims.modules.contract.entity.Contract;
import vn.edu.hust.vha.hims.modules.contract.entity.ContractType;
import vn.edu.hust.vha.hims.modules.contract.repository.ContractRepository;
import vn.edu.hust.vha.hims.modules.contract.repository.ContractTypeRepository;
import vn.edu.hust.vha.hims.modules.contract.service.ContractService;
import vn.edu.hust.vha.hims.modules.organization.entity.Decision;
import vn.edu.hust.vha.hims.modules.organization.repository.DecisionRepository;

@Service
@RequiredArgsConstructor
public class ContractServiceImpl implements ContractService {

    private final ContractRepository contractRepository;
    private final EmployeeRepository employeeRepository;
    private final ContractTypeRepository contractTypeRepository;
    private final DecisionRepository decisionRepository;

    @Override
    @Transactional
    public ContractResponseDTO createContract(ContractCreateDTO dto) {
        // 1. Validate và lấy Employee
        Employee employee = employeeRepository.findById(dto.getEmployeeId())
            .orElseThrow(() -> new RuntimeException("Employee not found with id: " + dto.getEmployeeId()));
        
        // 2. Validate và lấy ContractType
        ContractType contractType = contractTypeRepository.findById(dto.getContractTypeId())
            .orElseThrow(() -> new RuntimeException("Contract type not found with id: " + dto.getContractTypeId()));
        
        // 3. Validate và lấy Decision (nếu có)
        Decision decision = null;
        if (dto.getDecisionId() != null) {
            decision = decisionRepository.findById(dto.getDecisionId())
                .orElseThrow(() -> new RuntimeException("Decision not found with id: " + dto.getDecisionId()));
        }
        
        // 4. Tạo Contract entity
        Contract contract = Contract.builder()
            .employee(employee)
            .contractType(contractType)
            .decision(decision)
            .startDate(dto.getStartDate())
            .endDate(dto.getEndDate())
            .salaryAgreed(dto.getSalaryAgreed())
            .status(ContractStatus.ACTIVE)
            .contractUrl(dto.getContractUrl())
            .build();
        
        // 5. Lưu vào database
        Contract savedContract = contractRepository.save(contract);
        
        // 6. Map sang DTO và trả về
        return mapToResponseDTO(savedContract);
    }

    @Override
    @Transactional(readOnly = true)
    public ContractResponseDTO getContractById(UUID id) {
        Contract contract = contractRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Contract not found with id: " + id));
        
        return mapToResponseDTO(contract);
    }

    // Helper method để map Entity -> DTO
    private ContractResponseDTO mapToResponseDTO(Contract contract) {
        return ContractResponseDTO.builder()
            .contractId(contract.getId())
            .employeeId(contract.getEmployee().getId())
            .employeeFullName(contract.getEmployee().getFirstName() + " " + contract.getEmployee().getLastName())
            .contractTypeId(contract.getContractType().getId())
            .contractTypeName(contract.getContractType().getName())
            .decisionId(contract.getDecision() != null ? contract.getDecision().getId() : null)
            .decisionNumber(contract.getDecision() != null ? contract.getDecision().getDecisionNumber() : null)
            .salaryAgreed(contract.getSalaryAgreed())
            .status(contract.getStatus())
            .startDate(contract.getStartDate())
            .endDate(contract.getEndDate())
            .contractUrl(contract.getContractUrl())
            .createdAt(contract.getCreatedAt())
            .updatedAt(contract.getUpdatedAt())
            .build();
    }
}