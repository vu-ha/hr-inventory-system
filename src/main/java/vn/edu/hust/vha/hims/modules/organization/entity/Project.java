package vn.edu.hust.vha.hims.modules.organization.entity;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.edu.hust.vha.hims.common.enumeration.ProjectStatus;


@Entity
@Table(name = "project", schema = "hrm")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(
        name = "id",
        updatable = false,
        nullable = false,
        columnDefinition = "UUID"
    )
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "expected_end_date")
    private LocalDate expectedEndDate;

    // Giả định bạn đã tạo Enum ProjectStatus với các giá trị PLANNED, ONGOING, COMPLETED, CANCELLED
    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 20)
    private ProjectStatus status;

    // Mối quan hệ One-to-Many với ProjectMember
    @OneToMany(
        mappedBy = "project", // Tên trường Project trong ProjectMember
        cascade = CascadeType.ALL,
        fetch = FetchType.LAZY
    )
    private List<ProjectMember> members;
}