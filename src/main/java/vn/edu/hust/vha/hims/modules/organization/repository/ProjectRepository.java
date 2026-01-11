package vn.edu.hust.vha.hims.modules.organization.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import vn.edu.hust.vha.hims.modules.organization.entity.Project;
import vn.edu.hust.vha.hims.modules.organization.mapper.dto.response.ProjectSummaryDTO;

public interface ProjectRepository extends JpaRepository<Project, UUID>{
	boolean existsByName(String name);
	
	@Query("SELECT new vn.edu.hust.vha.hims.modules.organization.mapper.dto.response.ProjectSummaryDTO(" +
	           "p.id, p.name, p.startDate, p.expectedEndDate, p.status, " +
	           "CONCAT(m.firstName, ' ', m.lastName), " +
	           "(SELECT COUNT(pm) FROM ProjectMember pm WHERE pm.project = p.id)) " +
	           "FROM Project p " +
	           "JOIN Employee m ON p.manager.id = m.id")
	    List<ProjectSummaryDTO> getAllProjectSummaries();

	    @Query("SELECT new vn.edu.hust.vha.hims.modules.organization.mapper.dto.response.ProjectSummaryDTO(" +
	           "p.id, p.name, p.startDate, p.expectedEndDate, p.status, " +
	           "CONCAT(m.firstName, ' ', m.lastName), " +
	           "(SELECT COUNT(pm) FROM ProjectMember pm WHERE pm.project = p.id)) " +
	           "FROM Project p " +
	           "JOIN Employee m ON p.manager.id = m.id " +
	           "JOIN ProjectMember pmem ON p.id = pmem.project " +
	           "WHERE pmem.employee = :employeeId")
	    List<ProjectSummaryDTO> getMyProjectSummaries(@Param("employeeId") UUID employeeId);
	}
