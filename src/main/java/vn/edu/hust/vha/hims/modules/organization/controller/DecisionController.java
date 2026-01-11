package vn.edu.hust.vha.hims.modules.organization.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import vn.edu.hust.vha.hims.modules.organization.mapper.dto.request.DecisionCreateDTO;
import vn.edu.hust.vha.hims.modules.organization.mapper.dto.response.DecisionResponseDTO;
import vn.edu.hust.vha.hims.modules.organization.service.DecisionService;

@RestController
@RequestMapping("/api/v1/decisions")
@RequiredArgsConstructor
public class DecisionController {

    private final DecisionService decisionService;

    /**
     * Tạo hợp đồng mới
     * POST /api/v1/decisions
     */
    @PostMapping
    public ResponseEntity<DecisionResponseDTO> createDecision(
            @Valid @RequestBody DecisionCreateDTO request) {
        
        DecisionResponseDTO response = decisionService.createDecision(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}