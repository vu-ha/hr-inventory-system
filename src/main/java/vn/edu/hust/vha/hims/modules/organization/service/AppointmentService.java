package vn.edu.hust.vha.hims.modules.organization.service;

import vn.edu.hust.vha.hims.modules.organization.mapper.dto.request.AppointmentCreateDTO;
import vn.edu.hust.vha.hims.modules.organization.mapper.dto.response.AppointmentResponseDTO;

public interface AppointmentService {
	public AppointmentResponseDTO createAppointment(AppointmentCreateDTO dto);
}
