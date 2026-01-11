package vn.edu.hust.vha.hims.common.mapper.dto.response;

import java.util.UUID;
import vn.edu.hust.vha.hims.common.enumeration.Gender;

public record EmployeeSummaryDTO(
    UUID employeeId,
    String fullName,
    Gender gender,
    String email,
    String phoneNumber,
    Integer positionLevel,
    String positionName
) {}