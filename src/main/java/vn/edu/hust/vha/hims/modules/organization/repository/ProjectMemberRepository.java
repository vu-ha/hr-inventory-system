package vn.edu.hust.vha.hims.modules.organization.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import vn.edu.hust.vha.hims.common.key.ProjectMemberId;
import vn.edu.hust.vha.hims.modules.organization.entity.ProjectMember;

public interface ProjectMemberRepository extends JpaRepository<ProjectMember, ProjectMemberId>{
    
    boolean existsByProjectAndEmployee(UUID projectId, UUID employeeId);
    void deleteByProjectAndEmployee(UUID projectId, UUID employeeId);
    
    @Query("SELECT pm FROM ProjectMember pm " +
            "JOIN FETCH pm.employeeEntity " +
            "WHERE pm.project = :projectId")
     List<ProjectMember> findAllByProjectId(@Param("projectId") UUID projectId);
}
