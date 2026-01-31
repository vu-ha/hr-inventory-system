package vn.edu.hust.vha.hims.modules.organization.mapper.dto.request;

import java.util.List;

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
public class AppointmentWithDecisionDTO {
    
    @NotNull(message = "Decision information is required")
    @Valid
    private DecisionCreateDTO decision;
    
    @NotEmpty(message = "At least one appointment is required")
    @Valid
    private List<AppointmentCreateDTO> appointments;
}