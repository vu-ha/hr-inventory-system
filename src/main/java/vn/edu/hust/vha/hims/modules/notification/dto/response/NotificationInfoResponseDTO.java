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
public class NotificationInfoResponseDTO {
    private UUID id;
    private String title;
    private String content;
    private NotificationType type;
    private NotificationPriority priority;
    private LocalDateTime createdAt;
    private UUID createdBy;
}
