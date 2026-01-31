package vn.edu.hust.vha.hims.modules.organization.mapper.dto.request;

import java.util.List;
import java.util.UUID;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BatchAppointmentCreateDTO {
    
    @NotNull(message = "Decision ID is required")
    private UUID decisionId;
    
    @NotEmpty(message = "Appointments list cannot be empty")
    @Valid
    private List<AppointmentCreateDTO> appointments;
}