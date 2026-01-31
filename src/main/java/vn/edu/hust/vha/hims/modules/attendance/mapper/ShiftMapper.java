package vn.edu.hust.vha.hims.modules.attendance.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import vn.edu.hust.vha.hims.modules.attendance.entity.Shift;
import vn.edu.hust.vha.hims.modules.attendance.mapper.dto.request.ShiftCreateDTO;
import vn.edu.hust.vha.hims.modules.attendance.mapper.dto.response.ShiftResponseDTO;

// Thêm unmappedTargetPolicy để tránh cảnh báo về các trường Audit không có trong DTO
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ShiftMapper { // Đổi từ class thành interface

    // Map từ Request DTO sang Entity (Lúc tạo mới)
    Shift toEntity(ShiftCreateDTO dto);

    // Map từ Entity sang Response DTO (Lúc trả về cho Client)
    ShiftResponseDTO toResponse(Shift shift);
}