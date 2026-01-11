package vn.edu.hust.vha.hims.modules.organization.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vn.edu.hust.vha.hims.modules.organization.mapper.dto.request.AppointmentCreateDTO;
import vn.edu.hust.vha.hims.modules.organization.mapper.dto.response.AppointmentResponseDTO;
import vn.edu.hust.vha.hims.modules.organization.service.AppointmentService;

@RestController
@RequestMapping("/api/v1/appointments")
@RequiredArgsConstructor
@Tag(name = "Appointment Management")
public class AppointmentController {

    private final AppointmentService appointmentService;

    @PostMapping
    @Operation(summary = "Tạo bổ nhiệm mới")
    public ResponseEntity<AppointmentResponseDTO> createAppointment(@Valid @RequestBody AppointmentCreateDTO dto) {
        // Gọi service xử lý logic nghiệp vụ phức tạp đã viết
        AppointmentResponseDTO response = appointmentService.createAppointment(dto);
        
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}