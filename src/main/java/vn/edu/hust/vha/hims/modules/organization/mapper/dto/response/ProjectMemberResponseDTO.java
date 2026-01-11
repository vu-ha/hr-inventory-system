package vn.edu.hust.vha.hims.modules.organization.mapper.dto.response;

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
public class ProjectMemberResponseDTO {
    private UUID projectId;
    private UUID employeeId;
    private String employeeName;
    private String email;      
    private String phoneNumber; 
    private String roleInProject;
    private LocalDate joinDate;
}