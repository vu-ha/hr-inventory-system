package vn.edu.hust.vha.hims.modules.attendance.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import vn.edu.hust.vha.hims.modules.attendance.entity.Shift;
import vn.edu.hust.vha.hims.modules.attendance.mapper.ShiftMapper;
import vn.edu.hust.vha.hims.modules.attendance.mapper.dto.request.ShiftCreateDTO;
import vn.edu.hust.vha.hims.modules.attendance.mapper.dto.response.ShiftResponseDTO;
import vn.edu.hust.vha.hims.modules.attendance.repository.ShiftRepository;
import vn.edu.hust.vha.hims.modules.attendance.service.ShiftService;

@Service
@RequiredArgsConstructor
@Transactional
public class ShiftServiceImpl implements ShiftService {
	private final ShiftRepository shiftRepository;
	private final ShiftMapper shiftMapper;
	
	@Override
	public ShiftResponseDTO createShift(ShiftCreateDTO dto) {
		Shift shift = Shift.builder()
				.name(dto.getName())
				.startTime(dto.getStartTime())
				.endTime(dto.getEndTime())
				.workDay(dto.getWorkDay())
				.build();
		
		Shift saved = shiftRepository.save(shift);
		return shiftMapper.toResponse(saved);
	}
}
