package vn.edu.hust.vha.hims.modules.organization.entity;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.edu.hust.vha.hims.common.entity.Employee; // Import lớp Employee mẫu
import vn.edu.hust.vha.hims.common.key.ProjectMemberId;

@Entity
@Table(name = "project_member", schema = "hrm")
@IdClass(ProjectMemberId.class) // Sử dụng IdClass cho khóa chính kép
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectMember {
    @Id
    @Column(name = "project_id", columnDefinition = "UUID")
    private UUID project;

    @Id
    @Column(name = "employee_id", columnDefinition = "UUID")
    private UUID employee;


    @Column(name = "role_in_project", length = 50)
    private String roleInProject;

    @Column(name = "join_date", columnDefinition = "DATE")
    private LocalDate joinDate;


    @ManyToOne
    @JoinColumn(name = "project_id", insertable = false, updatable = false)
    private Project projectEntity; 


    @ManyToOne
    @JoinColumn(name = "employee_id", insertable = false, updatable = false)
    private Employee employeeEntity;
}