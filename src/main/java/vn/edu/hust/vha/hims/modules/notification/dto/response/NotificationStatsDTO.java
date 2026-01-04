package vn.edu.hust.vha.hims.modules.notification.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class NotificationStatsDTO {
    private Long total;
    private Long read;
}