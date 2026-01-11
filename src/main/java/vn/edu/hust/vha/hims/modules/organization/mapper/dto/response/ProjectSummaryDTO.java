package vn.edu.hust.vha.hims.modules.organization.mapper.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.edu.hust.vha.hims.common.enumeration.ProjectStatus;

import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor 
public class ProjectSummaryDTO {
    private UUID id;
    private String name;
    private LocalDate startDate;
    private LocalDate expectedEndDate;
    private ProjectStatus status;
    private String pmFullName;
    private Long memberCount;
}