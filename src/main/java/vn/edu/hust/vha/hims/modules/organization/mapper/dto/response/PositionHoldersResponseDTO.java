package vn.edu.hust.vha.hims.modules.organization.mapper.dto.response;

import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PositionHoldersResponseDTO {
    private UUID positionId;
    private String positionName;
    private String description;
    private String jobGradeName;
    private int managementLevel;
    private int totalHolders;
    private List<AppointmentResponseDTO> currentHolders;
}