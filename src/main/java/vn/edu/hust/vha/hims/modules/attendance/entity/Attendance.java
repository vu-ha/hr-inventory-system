package vn.edu.hust.vha.hims.modules.attendance.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.edu.hust.vha.hims.common.entity.BaseEntity;
import vn.edu.hust.vha.hims.common.enumeration.AttendanceType;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "attendance", schema = "hrm")
@Getter
@Setter
public class Attendance extends BaseEntity {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shift_assignment_id", nullable = false)
    private ShiftAssignment shiftAssignment;

    @Column(name = "time_in")
    private LocalDateTime timeIn;

    @Column(name = "time_out")
    private LocalDateTime timeOut;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 20)
    private AttendanceType status; // LATE, EARLY_LEAVE, ON_TIME, ABSENT
}