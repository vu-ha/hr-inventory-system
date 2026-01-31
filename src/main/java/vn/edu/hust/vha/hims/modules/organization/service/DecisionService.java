package vn.edu.hust.vha.hims.modules.organization.service;

import java.time.LocalDate;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import vn.edu.hust.vha.hims.common.enumeration.DecisionType;
import vn.edu.hust.vha.hims.modules.organization.mapper.dto.request.DecisionCreateDTO;
import vn.edu.hust.vha.hims.modules.organization.mapper.dto.response.DecisionDetailResponseDTO;
import vn.edu.hust.vha.hims.modules.organization.mapper.dto.response.DecisionResponseDTO;

public interface DecisionService {
    
    /**
     * Tạo quyết định mới
     */
    DecisionResponseDTO createDecision(DecisionCreateDTO dto);
    
    /**
     * Lấy chi tiết quyết định kèm danh sách bổ nhiệm
     */
    DecisionDetailResponseDTO getDecisionDetail(UUID decisionId);
    
    /**
     * Lấy danh sách quyết định với filter và phân trang
     */
    Page<DecisionResponseDTO> getDecisions(
            UUID employeeId, 
            DecisionType decisionType, 
            LocalDate fromDate, 
            LocalDate toDate, 
            Pageable pageable);
    
    /**
     * Cập nhật quyết định (chỉ được phép nếu chưa có hiệu lực)
     */
    DecisionResponseDTO updateDecision(UUID decisionId, DecisionCreateDTO dto);
    
    /**
     * Xóa quyết định (chỉ được phép nếu chưa có bổ nhiệm nào)
     */
    void deleteDecision(UUID decisionId);
}