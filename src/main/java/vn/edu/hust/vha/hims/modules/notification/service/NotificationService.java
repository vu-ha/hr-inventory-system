package vn.edu.hust.vha.hims.modules.notification.service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import vn.edu.hust.vha.hims.common.enumeration.NotificationType;
import vn.edu.hust.vha.hims.modules.notification.dto.response.EmployeeNotificationResponseDTO;
import vn.edu.hust.vha.hims.modules.notification.dto.response.ManangerNotificationResponseDTO;

public interface NotificationService {
    Page<EmployeeNotificationResponseDTO> getNotificationsF1(
            UUID employeeId,
            NotificationType type,
            Pageable pageable
    );

    Page<EmployeeNotificationResponseDTO> getNotificationsF2(
            UUID employeeId,
            NotificationType type,
            Pageable pageable
    );
    
    ManangerNotificationResponseDTO getNotificationDetail(UUID notificationId);
}
