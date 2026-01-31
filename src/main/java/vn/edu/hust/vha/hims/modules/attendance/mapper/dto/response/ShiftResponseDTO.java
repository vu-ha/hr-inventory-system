package vn.edu.hust.vha.hims.modules.attendance.mapper.dto.response;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShiftResponseDTO {
	private UUID id;
    private String name;
    private LocalTime startTime;
    private LocalTime endTime;
    private Float workDay;
    
    private LocalDateTime updateAt;
}
