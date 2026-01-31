package vn.edu.hust.vha.hims.modules.attendance.service;

import vn.edu.hust.vha.hims.modules.attendance.mapper.dto.request.ShiftCreateDTO;
import vn.edu.hust.vha.hims.modules.attendance.mapper.dto.response.ShiftResponseDTO;

public interface ShiftService {
	public ShiftResponseDTO createShift(ShiftCreateDTO dto);
	
}	
