package vn.edu.hust.vha.hims.modules.notification.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import vn.edu.hust.vha.hims.common.enumeration.NotificationType;
import vn.edu.hust.vha.hims.modules.notification.dto.response.EmployeeNotificationResponseDTO;
import vn.edu.hust.vha.hims.modules.notification.dto.response.NotificationInfoResponseDTO;
import vn.edu.hust.vha.hims.modules.notification.entity.Notification;
@Repository
public interface NotificationRepository extends JpaRepository<Notification, UUID> {
	/**
	 * Nhân viên lấy ra danh sách thông báo gửi đến và phân loại theo  NotificationType
	 */
	//F1
	Page<Notification> findByRecipients_Employee_IdAndTypeOrderByCreatedAtAsc(
			UUID employeeId,
			NotificationType type, 
			Pageable pageable
	);
	
	//F2 - Thứ tự SELECT trong Query phải khớp với thứ tự trong DTO
	@Query("""
			SELECT new vn.edu.hust.vha.hims.modules.notification.dto.response.EmployeeNotificationResponseDTO(
			     r.employee.id,
			     r.isRead,
			     r.readAt,
			     n.id,
			     n.title,
			     n.content,
			     n.type,
			     n.priority,
			     n.createdAt
			)
			FROM Notification n
			JOIN n.recipients r
			WHERE r.employee.id = :employeeId 
				AND (:notificationType IS NULL OR n.type = :notificationType)
			ORDER BY n.createdAt ASC
			""")
	Page<EmployeeNotificationResponseDTO> findNotificationForEmployee(
			@Param("employeeId") UUID employeeId,
			@Param("notificationType") NotificationType type,	
			Pageable pageable
	);
	
	/**
	 * Mananger lấy ra danh sách thông báo
	 */
	
	@Query("""
			SELECT new vn.edu.hust.vha.hims.modules.notification.dto.response.NotificationInfoResponseDTO(
				n.id,
				n.title,
				n.content,
				n.type,
			    n.priority,
			    n.createdAt,
			    n.createdBy			
			)
			FROM Notification n 
			WHERE n.id = :notificationId
			""")
	NotificationInfoResponseDTO findNotificationInfor( @Param("notificationId") UUID notificationId);
}