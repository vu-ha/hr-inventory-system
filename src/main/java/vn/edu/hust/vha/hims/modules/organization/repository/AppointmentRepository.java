package vn.edu.hust.vha.hims.modules.organization.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import vn.edu.hust.vha.hims.common.enumeration.AppointmentStatus;
import vn.edu.hust.vha.hims.modules.organization.entity.Appointment;
@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, UUID>,
                                               JpaSpecificationExecutor<Appointment> {
	// Tìm bổ nhiệm chính đang hoạt động của nhân viên
    @Query("SELECT a FROM Appointment a WHERE a.employee.id = :empId AND a.isPrimary = true AND a.status = 'ACTIVE'")
    Optional<Appointment> findPrimaryActive(UUID empId);

    // Tìm tất cả bổ nhiệm đang hoạt động
    //List<Appointment> findByEmployeeIdAndStatus(UUID empId, String status);
    List<Appointment> findByEmployeeIdAndStatus(UUID empId, AppointmentStatus status);
    
    List<Appointment> findByEmployeeIdOrderByStartDateDesc(UUID employeeId);
}
