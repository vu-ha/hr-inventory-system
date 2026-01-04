package vn.edu.hust.vha.hims.modules.notification.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import vn.edu.hust.vha.hims.modules.notification.dto.response.EmployeeReadStatusDTO;
import vn.edu.hust.vha.hims.modules.notification.dto.response.NotificationStatsDTO;
import vn.edu.hust.vha.hims.modules.notification.entity.NotificationRecipient;

@Repository
public interface RecipientRepository extends JpaRepository<NotificationRecipient, UUID>  {
	
	@Query("""
			SELECT new vn.edu.hust.vha.hims.modules.notification.dto.response.EmployeeReadStatusDTO(
				e.id,
				CONCAT(e.firstName, ' ', e.lastName) as full_name,
				nr.isRead,
				nr.readAt
			)
			FROM NotificationRecipient  nr
			JOIN nr.employee e
			WHERE nr.notification.id = :notificationId
			""")
	List<EmployeeReadStatusDTO> findRecipientsWithStatus(@Param("notificationId") UUID notificationId);
	
	@Query("""
		    SELECT 
		        COUNT(nr),
		        SUM(CASE WHEN nr.isRead = true THEN 1 ELSE 0 END)
		    FROM NotificationRecipient nr
		    WHERE nr.notification.id = :notificationId
		""")
	NotificationStatsDTO getReadStats(@Param("notificationId") UUID notificationId);
}
