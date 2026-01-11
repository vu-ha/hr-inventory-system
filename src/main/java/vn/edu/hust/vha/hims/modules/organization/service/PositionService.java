package vn.edu.hust.vha.hims.modules.organization.service;

import java.util.List;

import vn.edu.hust.vha.hims.modules.organization.mapper.dto.response.PositionResponseDTO;

public interface PositionService {
	List<PositionResponseDTO> getAllPosition();
}
