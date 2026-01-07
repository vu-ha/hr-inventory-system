package vn.edu.hust.vha.hims.modules.organization.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.edu.hust.vha.hims.modules.organization.entity.Department;
@Repository
public interface DepartmentRepository extends JpaRepository<Department, UUID>{

}
