package vn.edu.hust.vha.hims.modules.organization.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import vn.edu.hust.vha.hims.modules.organization.entity.Position;
import vn.edu.hust.vha.hims.modules.organization.mapper.dto.response.PositionResponseDTO;
import vn.edu.hust.vha.hims.modules.organization.repository.PositionRepository;


@RestController
@RequestMapping("/api/positions")
@RequiredArgsConstructor 
public class PositionController {

    private final PositionRepository positionRepository;

    @GetMapping
    public ResponseEntity<List<PositionResponseDTO>> getAllPositions() {
    	List<Position> positions = positionRepository.findAll();
        // map sang DTO
        List<PositionResponseDTO> dtos = positions.stream()
            .map(p -> new PositionResponseDTO(p.getId(), p.getName(), p.getDescription(), 
            		                          p.getJobGrade().getName(), p.getManagementLevel()))
            .toList();

        return ResponseEntity.ok(dtos);
    }
}
