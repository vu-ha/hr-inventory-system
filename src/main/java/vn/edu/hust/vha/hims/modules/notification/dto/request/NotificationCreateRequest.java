package vn.edu.hust.vha.hims.modules.notification.dto.request;

import vn.edu.hust.vha.hims.common.enumeration.NotificationPriority;
import vn.edu.hust.vha.hims.common.enumeration.NotificationType;
import java.util.List;
import java.util.UUID;

public record NotificationCreateRequest(
    String title,
    String content,
    NotificationType type,
    NotificationPriority priority,
    UUID createdBy,      // ID Manager nhập thủ công
    List<UUID> employeeIds // Danh sách nhân viên nhận tin
) {}