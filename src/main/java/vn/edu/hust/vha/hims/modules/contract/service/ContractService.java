package vn.edu.hust.vha.hims.modules.contract.service;

import java.util.UUID;
import vn.edu.hust.vha.hims.modules.contract.dto.ContractCreateDTO;
import vn.edu.hust.vha.hims.modules.contract.dto.ContractResponseDTO;

public interface ContractService {
    ContractResponseDTO createContract(ContractCreateDTO dto);
    ContractResponseDTO getContractById(UUID id);
}