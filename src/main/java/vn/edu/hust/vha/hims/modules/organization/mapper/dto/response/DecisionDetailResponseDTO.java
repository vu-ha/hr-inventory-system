package vn.edu.hust.vha.hims.modules.organization.mapper.dto.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DecisionDetailResponseDTO {
    private DecisionResponseDTO decision;
    private List<AppointmentResponseDTO> appointments;
}