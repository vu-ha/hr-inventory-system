package vn.edu.hust.vha.hims.modules.notification.dto.response;

import java.time.LocalDateTime;
import java.util.List;
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

public class ManangerNotificationResponseDTO {
    private UUID notificationId;
    private String title;
    private String content;
    private NotificationType type; 
    private NotificationPriority priority;
    private UUID createdBy;
    private LocalDateTime createdAt;
    
    // Thống kê
    private int totalRecipients;
    private int readCount;
    private int unreadCount;
    
    private List<EmployeeReadStatusDTO> Recipients;  
}