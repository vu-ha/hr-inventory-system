package vn.edu.hust.vha.hims.modules.attendance.entity;

import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.edu.hust.vha.hims.common.entity.BaseEntity;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "shift", schema = "hrm")
@Getter
@Setter
@Builder
public class Shift extends BaseEntity {

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "start_time", nullable = false)
    private LocalTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalTime endTime;

    @Column(name = "work_day")
    private Float workDay;
}