package vn.edu.hust.vha.hims.modules.notification.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import vn.edu.hust.vha.hims.common.enumeration.NotificationType;
import vn.edu.hust.vha.hims.modules.notification.dto.response.EmployeeNotificationResponseDTO;
import vn.edu.hust.vha.hims.modules.notification.dto.response.EmployeeReadStatusDTO;
import vn.edu.hust.vha.hims.modules.notification.dto.response.ManangerNotificationResponseDTO;
import vn.edu.hust.vha.hims.modules.notification.dto.response.NotificationInfoResponseDTO;
import vn.edu.hust.vha.hims.modules.notification.dto.response.NotificationStatsDTO;
import vn.edu.hust.vha.hims.modules.notification.entity.Notification;
import vn.edu.hust.vha.hims.modules.notification.entity.NotificationRecipient;
import vn.edu.hust.vha.hims.modules.notification.repository.NotificationRepository;
import vn.edu.hust.vha.hims.modules.notification.repository.RecipientRepository;
import vn.edu.hust.vha.hims.modules.notification.service.NotificationService;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {
	@Autowired
    private NotificationRepository notificationRepository;
	@Autowired
	private RecipientRepository recipientRepository;

    public Page<EmployeeNotificationResponseDTO> getNotificationsF1(UUID employeeId, NotificationType type, Pageable pageable) {
        Page<Notification> notificationPage = notificationRepository.findByRecipients_Employee_IdAndTypeOrderByCreatedAtAsc(employeeId, type, pageable);

        return notificationPage.map(notification -> {
            // Tìm bản ghi recipient tương ứng với nhân viên này để lấy trạng thái isRead
            NotificationRecipient recipient = notification.getRecipients().stream()
                .filter(r -> r.getEmployee().getId().equals(employeeId))
                .findFirst()
                .orElse(null);

            return EmployeeNotificationResponseDTO.builder()
                .employeeId(employeeId)
                .notificationId(notification.getId())
                .title(notification.getTitle())
                .content(notification.getContent())
                .type(notification.getType())
                .priority(notification.getPriority()) 
                .createdAt(notification.getCreatedAt())
                .isRead(recipient != null ? recipient.getIsRead() : false)
                .readAt(recipient != null ? recipient.getReadAt() : null)
                .build();
        });
    }

    public Page<EmployeeNotificationResponseDTO> getNotificationsF2(UUID employeeId, NotificationType type, Pageable pageable) {
        // Chỉ cần gọi và trả về, không cần xử lý gì thêm
        return notificationRepository.findNotificationForEmployee(employeeId, type, pageable);
    }
    
    public ManangerNotificationResponseDTO getNotificationDetail(UUID notificationId){
    	 NotificationInfoResponseDTO info = notificationRepository.findNotificationInfor(notificationId);
    	 
    	 List<EmployeeReadStatusDTO> recipients =  recipientRepository.findRecipientsWithStatus(notificationId);
    	 
    	 NotificationStatsDTO stats = recipientRepository.getReadStats(notificationId);
    	 
    	 return ManangerNotificationResponseDTO.builder()
    	            .notificationId(info.getId())
    	            .title(info.getTitle())
    	            .content(info.getContent())
    	            .type(info.getType())
    	            .priority(info.getPriority())
    	            .createdBy(info.getCreatedBy())
    	            .createdAt(info.getCreatedAt())
    	            .totalRecipients(stats.getTotal().intValue()) // Chuyển Long sang int
    	            .readCount(stats.getRead().intValue())
    	            .unreadCount((int) (stats.getTotal() - stats.getRead()))
    	            .Recipients(recipients)
    	            .build();
    }
}
