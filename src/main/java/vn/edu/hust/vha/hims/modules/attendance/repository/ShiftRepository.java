package vn.edu.hust.vha.hims.modules.attendance.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import vn.edu.hust.vha.hims.modules.attendance.entity.Shift;

public interface ShiftRepository extends JpaRepository<Shift, UUID>{

}
