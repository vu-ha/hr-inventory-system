package vn.edu.hust.vha.hims.modules.contract.controller;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import vn.edu.hust.vha.hims.modules.contract.dto.ContractCreateDTO;
import vn.edu.hust.vha.hims.modules.contract.dto.ContractResponseDTO;
import vn.edu.hust.vha.hims.modules.contract.service.ContractService;

@RestController
@RequestMapping("/api/v1/contracts")
@RequiredArgsConstructor
public class ContractController {

    private final ContractService contractService;

    /**
     * Tạo hợp đồng mới
     * POST /api/v1/contracts
     */
    @PostMapping
    public ResponseEntity<ContractResponseDTO> createContract(
            @Valid @RequestBody ContractCreateDTO request) {
        
        ContractResponseDTO response = contractService.createContract(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Lấy thông tin hợp đồng theo ID
     * GET /api/v1/contracts/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<ContractResponseDTO> getContractById(
            @PathVariable UUID id) {
        
        ContractResponseDTO response = contractService.getContractById(id);
        return ResponseEntity.ok(response);
    }
}