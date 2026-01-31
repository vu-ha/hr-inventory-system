package vn.edu.hust.vha.hims.modules.auth.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import vn.edu.hust.vha.hims.common.entity.BaseEntity;
import vn.edu.hust.vha.hims.common.entity.Employee;
import vn.edu.hust.vha.hims.common.enumeration.AccountStatus;
import vn.edu.hust.vha.hims.common.enumeration.RoleType;
@Entity
@Table(name = "user_account", schema = "hrm")
@Getter
@Setter
public class UserAccount extends BaseEntity {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", unique = true)
    private Employee employee;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(name = "password_hash", nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private AccountStatus status;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
        name = "user_roles", 
        schema = "hrm", 
        joinColumns = @JoinColumn(name = "user_id")
    )
    @Enumerated(EnumType.STRING)
    @Column(name = "role_name") //"ROLE_ADMIN", "ROLE_USER",...
    private Set<RoleType> roles = new HashSet<>();
}