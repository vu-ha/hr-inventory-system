package vn.edu.hust.vha.hims.common.mapper.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ValidationResponseDTO {
    private boolean exists;
    private String message;
}