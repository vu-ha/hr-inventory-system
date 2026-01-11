package vn.edu.hust.vha.hims.common.mapper;
import org.mapstruct.Mapper;

import vn.edu.hust.vha.hims.common.entity.Employee;
import vn.edu.hust.vha.hims.common.mapper.dto.request.EmployeeCreateDTO;
import vn.edu.hust.vha.hims.common.mapper.dto.response.EmployeeResponseDTO;

@Mapper(componentModel = "spring") // Giúp Spring Boot quản lý Mapper như một Bean
public interface EmployeeMapper {

    // MapStruct tự khớp các trường cùng tên từ DTO sang Entity
    Employee toEntity(EmployeeCreateDTO dto);

    // Map ngược lại từ Entity sang Response DTO
    EmployeeResponseDTO toResponse(Employee employee);
}