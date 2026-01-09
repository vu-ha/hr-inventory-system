package vn.edu.hust.vha.hims.modules.organization.dto.request;

import java.time.LocalDate;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProjectMemberRequestDTO {
    private UUID employeeId;
    private String roleInProject;
    private LocalDate joinDate;
}