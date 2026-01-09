package vn.edu.hust.vha.hims.modules.organization.service.impl;

import java.util.List;

import vn.edu.hust.vha.hims.modules.organization.dto.response.PositionResponseDTO;
import vn.edu.hust.vha.hims.modules.organization.entity.Position;
import vn.edu.hust.vha.hims.modules.organization.repository.PositionRepository;
import vn.edu.hust.vha.hims.modules.organization.service.PositionService;

public class PositionServiceImpl implements PositionService {
	
	private PositionRepository positionRepository;
	
	public List<PositionResponseDTO> getAllPosition() {
	    List<Position> positions = positionRepository.findAll();
	    return positions.stream()
	            .map(p -> new PositionResponseDTO(p.getId(), p.getName(), p.getDescription(), 
	            		                          p.getJobGrade().getName(), p.getManagementLevel()))
	            .toList();
	}

}
