package vn.edu.hust.vha.hims.modules.organization.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import vn.edu.hust.vha.hims.modules.organization.entity.Decision;

public interface DecisionRepository extends JpaRepository<Decision, UUID>{

}
