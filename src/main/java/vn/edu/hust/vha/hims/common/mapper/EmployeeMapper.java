package vn.edu.hust.vha.hims.common.mapper;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import vn.edu.hust.vha.hims.common.entity.Employee;
import vn.edu.hust.vha.hims.common.mapper.dto.request.EmployeeCreateDTO;
import vn.edu.hust.vha.hims.common.mapper.dto.request.EmployeeUpdateDTO;
import vn.edu.hust.vha.hims.common.mapper.dto.response.EmployeeResponseDTO;

@Mapper(componentModel = "spring",  unmappedTargetPolicy = ReportingPolicy.IGNORE) // Giúp Spring Boot quản lý Mapper như một Bean
public interface EmployeeMapper {

    // MapStruct tự khớp các trường cùng tên từ DTO sang Entity
    Employee toEntity(EmployeeCreateDTO dto);

    // Map ngược lại từ Entity sang Response DTO
    EmployeeResponseDTO toResponse(Employee employee);
    
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(EmployeeUpdateDTO dto, @MappingTarget Employee employee);
}