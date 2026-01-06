package vn.edu.hust.vha.hims.modules.organization.service;

import vn.edu.hust.vha.hims.modules.organization.dto.request.DecisionCreateDTO;
import vn.edu.hust.vha.hims.modules.organization.dto.response.DecisionResponseDTO;

public interface DecisionService {
	public DecisionResponseDTO createDecision(DecisionCreateDTO dto);
}
