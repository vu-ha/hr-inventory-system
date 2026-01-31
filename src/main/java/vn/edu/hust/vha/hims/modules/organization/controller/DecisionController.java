package vn.edu.hust.vha.hims.modules.organization.controller;

import java.time.LocalDate;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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
import vn.edu.hust.vha.hims.common.enumeration.DecisionType;
import vn.edu.hust.vha.hims.modules.organization.mapper.dto.request.DecisionCreateDTO;
import vn.edu.hust.vha.hims.modules.organization.mapper.dto.response.DecisionDetailResponseDTO;
import vn.edu.hust.vha.hims.modules.organization.mapper.dto.response.DecisionResponseDTO;
import vn.edu.hust.vha.hims.modules.organization.service.DecisionService;

@RestController
@RequestMapping("/api/decisions")
@RequiredArgsConstructor
public class DecisionController {
    
    private final DecisionService decisionService;

    /**
     * 1. POST /api/decisions - Tạo quyết định mới
     */
    @PostMapping
    public ResponseEntity<DecisionResponseDTO> createDecision(
            @Valid @RequestBody DecisionCreateDTO dto) {
        DecisionResponseDTO response = decisionService.createDecision(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * 2. GET /api/decisions/{id} - Xem chi tiết quyết định và các bổ nhiệm liên quan
     */
    @GetMapping("/{id}")
    public ResponseEntity<DecisionDetailResponseDTO> getDecisionDetail(
            @PathVariable UUID id) {
        DecisionDetailResponseDTO response = decisionService.getDecisionDetail(id);
        return ResponseEntity.ok(response);
    }

    /**
     * 3. GET /api/decisions - Lấy danh sách quyết định (phân trang, filter)
     */
    @GetMapping
    public ResponseEntity<Page<DecisionResponseDTO>> getDecisions(
            @RequestParam(required = false) UUID employeeId,
            @RequestParam(required = false) DecisionType decisionType,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate,
            Pageable pageable) {
        Page<DecisionResponseDTO> response = decisionService.getDecisions(
                employeeId, decisionType, fromDate, toDate, pageable);
        return ResponseEntity.ok(response);
    }

    /**
     * 4. PUT /api/decisions/{id} - Cập nhật quyết định (trước khi có hiệu lực)
     */
    @PutMapping("/{id}")
    public ResponseEntity<DecisionResponseDTO> updateDecision(
            @PathVariable UUID id,
            @Valid @RequestBody DecisionCreateDTO dto) {
        DecisionResponseDTO response = decisionService.updateDecision(id, dto);
        return ResponseEntity.ok(response);
    }

    /**
     * 5. DELETE /api/decisions/{id} - Xóa quyết định (nếu chưa có bổ nhiệm)
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDecision(@PathVariable UUID id) {
        decisionService.deleteDecision(id);
        return ResponseEntity.noContent().build();
    }
}