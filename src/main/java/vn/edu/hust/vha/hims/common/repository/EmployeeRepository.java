package vn.edu.hust.vha.hims.common.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import vn.edu.hust.vha.hims.common.entity.Employee;
import vn.edu.hust.vha.hims.common.mapper.dto.response.EmployeeSummaryDTO;


public interface EmployeeRepository extends JpaRepository<Employee, UUID>{
	@Query("""
		    SELECT new vn.edu.hust.vha.hims.common.mapper.dto.response.EmployeeSummaryDTO(
		        e.id,
		        CONCAT(e.firstName, ' ', e.lastName),
		        e.gender,
		        e.email,
		        e.phoneNumber,
				p.managementLevel,
				p.name
		    )
		    FROM Appointment ap
			JOIN ap.employee e
			JOIN ap.position p
			JOIN ap.department d
		    WHERE ap.isPrimary = true AND ap.endDate is null
		    	AND d.id = :departmentId
		    """)
		List<EmployeeSummaryDTO> findAllEmployee(@Param("departmentId") UUID departmentId);
}
