package vn.edu.hust.vha.hims.modules.notification.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class EmployeeReadStatusDTO {
	private UUID id;
	private String fullName;
	private boolean isRead;
	private LocalDateTime readAt;
}
