package vn.edu.hust.vha.hims.modules.notification.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.edu.hust.vha.hims.common.enumeration.NotificationPriority;
import vn.edu.hust.vha.hims.common.enumeration.NotificationType;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeNotificationResponseDTO {
	private UUID employeeId;
    private Boolean isRead;
    private LocalDateTime readAt;

    // Thông tin từ Notification
    private UUID notificationId;
    private String title;
    private String content;
    private NotificationType type; 
    private NotificationPriority priority;
    // Thông tin cơ bản từ BaseEntity của Notification
    private LocalDateTime createdAt;
}
