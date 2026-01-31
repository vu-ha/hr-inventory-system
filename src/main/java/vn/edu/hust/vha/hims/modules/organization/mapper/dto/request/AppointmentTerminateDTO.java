package vn.edu.hust.vha.hims.modules.organization.mapper.dto.request;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentTerminateDTO {
    
    @NotNull(message = "End date is required")
    private LocalDate endDate;
    
    private String reason;
}