package vn.edu.hust.vha.hims.modules.organization.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.hust.vha.hims.modules.organization.entity.Position;

public interface PositionRepository extends JpaRepository<Position, UUID> {
	List<Position> findAll();
}
