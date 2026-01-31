package vn.edu.hust.vha.hims.common.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import vn.edu.hust.vha.hims.common.entity.Employee;
import vn.edu.hust.vha.hims.common.enumeration.Gender;
import vn.edu.hust.vha.hims.common.enumeration.MaritalStatus;
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
	
	@Query("""
	        SELECT e FROM Employee e
	        WHERE LOWER(CONCAT(e.firstName, ' ', e.lastName)) LIKE LOWER(CONCAT('%', :keyword, '%'))
	            OR LOWER(e.email) LIKE LOWER(CONCAT('%', :keyword, '%'))
	            OR e.phoneNumber LIKE CONCAT('%', :keyword, '%')
	        """)
	    Page<Employee> searchEmployees(@Param("keyword") String keyword, Pageable pageable);
	    
	    // Find employees with filters
	@Query("""
		    SELECT DISTINCT e FROM Employee e
		    LEFT JOIN e.appointments ap
		    WHERE (CAST(:name AS string) IS NULL OR LOWER(CONCAT(e.firstName, ' ', e.lastName)) LIKE :name)
		        AND (CAST(:email AS string) IS NULL OR LOWER(e.email) LIKE :email)
		        AND (CAST(:gender AS string) IS NULL OR e.gender = :gender)
		        AND (CAST(:maritalStatus AS string) IS NULL OR e.maritalStatus = :maritalStatus)
		        AND (CAST(:departmentId AS uuid) IS NULL OR ap.department.id = :departmentId)
		        AND (CAST(:positionId AS uuid) IS NULL OR ap.position.id = :positionId)
		        AND (:yearJoining IS NULL OR e.yearJoining = :yearJoining)
		    """)
		Page<Employee> findEmployeesWithFilters(
		    @Param("name") String name,
		    @Param("email") String email,
		    @Param("gender") Gender gender,
		    @Param("maritalStatus") MaritalStatus maritalStatus,
		    @Param("departmentId") UUID departmentId,
		    @Param("positionId") UUID positionId,
		    @Param("yearJoining") Short yearJoining,
		    Pageable pageable
		);
	    
	    // Validation 
	    boolean existsByEmail(String email);
	    boolean existsByPhoneNumber(String phoneNumber);
	    boolean existsByTaxCode(String taxCode);
	    boolean existsBySocialInsuranceNumber(String socialInsuranceNumber);
	    
	    boolean existsByEmailAndIdNot(String email, UUID id);
	    boolean existsByPhoneNumberAndIdNot(String phoneNumber, UUID id);
	    boolean existsByTaxCodeAndIdNot(String taxCode, UUID id);
	    boolean existsBySocialInsuranceNumberAndIdNot(String socialInsuranceNumber, UUID id);
	    
	    Optional<Employee> findByEmail(String email);
	    Optional<Employee> findByPhoneNumber(String phoneNumber);
	    Optional<Employee> findByTaxCode(String taxCode);
	    Optional<Employee> findBySocialInsuranceNumber(String socialInsuranceNumber);
}
