package vn.edu.hust.vha.hims.modules.organization.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import vn.edu.hust.vha.hims.modules.organization.entity.Decision;
@Repository
public interface DecisionRepository extends JpaRepository<Decision, UUID>,
											JpaSpecificationExecutor<Decision> {

}
