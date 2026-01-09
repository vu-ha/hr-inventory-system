package vn.edu.hust.vha.hims.modules.organization.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import vn.edu.hust.vha.hims.modules.organization.entity.Position;
@Repository
public interface PositionRepository extends JpaRepository<Position, UUID> {
	List<Position> findAll();
	
	
	@Query("""
		    SELECT MIN(p.managementLevel)
		    FROM Decision d
		    JOIN d.employee e
		    JOIN e.appointments ap
		    JOIN ap.position p
		    WHERE e.id = :employeeId
		      AND ap.status = 'ACTIVE'
		      AND (ap.endDate IS NULL OR ap.endDate >= CURRENT_DATE)
		    """)
	Integer findHighestLevelByEmployeeId(@Param("employeeId") UUID employeeId);
}
