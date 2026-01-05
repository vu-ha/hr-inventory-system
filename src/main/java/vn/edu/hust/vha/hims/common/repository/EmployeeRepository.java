package vn.edu.hust.vha.hims.common.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import vn.edu.hust.vha.hims.common.dto.response.EmployeeResponseDTO;
import vn.edu.hust.vha.hims.common.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, UUID>{
	@Query("""
		    SELECT new vn.edu.hust.vha.hims.common.dto.response.EmployeeResponseDTO(
		        e.id,
		        CONCAT(e.firstName, ' ', e.lastName),
		        e.gender,
		        e.email,
		        e.phoneNumber,
		        p.name,
		        d.name
		    )
		    FROM Appointment a
		    JOIN a.employee e
		    JOIN a.department d
		    JOIN a.position p
		    WHERE d.id = :departmentId
		        AND a.endDate IS NULL
		    """)
		List<EmployeeResponseDTO> findAllEmployee(@Param("departmentId") UUID departmentId);
}
