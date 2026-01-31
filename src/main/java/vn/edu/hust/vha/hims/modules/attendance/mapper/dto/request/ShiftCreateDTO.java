package vn.edu.hust.vha.hims.modules.attendance.mapper.dto.request;

import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShiftCreateDTO {
    private String name;
    private LocalTime startTime;
    private LocalTime endTime;
    private Float workDay;
}
