package vn.edu.hust.vha.hims.modules.notification.controller;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import vn.edu.hust.vha.hims.common.enumeration.NotificationType;
import vn.edu.hust.vha.hims.modules.notification.dto.response.EmployeeNotificationResponseDTO;
import vn.edu.hust.vha.hims.modules.notification.dto.response.ManangerNotificationResponseDTO;
import vn.edu.hust.vha.hims.modules.notification.service.impl.NotificationServiceImpl;

@RestController
@RequestMapping("/api/v1/notifications")
@RequiredArgsConstructor // Tự động inject NotificationService thông qua Constructor
public class NotificationController {

    private final NotificationServiceImpl notificationService;

    /**
     * API lấy danh sách thông báo cho nhân viên (có phân trang và lọc theo loại)
     * URL ví dụ: /api/v1/notifications/employee/uuid-cua-nhan-vien?type=SYSTEM&page=0&size=10
     */
    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<Page<EmployeeNotificationResponseDTO>> getEmployeeNotifications(
            @PathVariable UUID employeeId,
            @RequestParam(required = false) NotificationType type,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "asc") String direction
    ) {
        // 1. Xử lý sắp xếp (Sorting)
        Sort sort = direction.equalsIgnoreCase("desc") 
                    ? Sort.by(sortBy).descending() 
                    : Sort.by(sortBy).ascending();

        // 2. Tạo đối tượng phân trang (Pageable)
        Pageable pageable = PageRequest.of(page, size, sort);

        // 3. Gọi Service để lấy dữ liệu
        Page<EmployeeNotificationResponseDTO> result = notificationService.getNotificationsF2(employeeId, type, pageable);

        // 4. Trả về HTTP 200 OK kèm dữ liệu JSON
        return ResponseEntity.ok(result);
    }
    
    @GetMapping("/manager/notifications/{id}")
    public ManangerNotificationResponseDTO getDetail(@PathVariable UUID id) {
        return notificationService.getNotificationDetail(id);
    }
}