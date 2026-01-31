package vn.edu.hust.vha.hims.modules.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.edu.hust.vha.hims.modules.auth.entity.UserAccount;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount, UUID> {
    Optional<UserAccount> findByUsername(String username);
    boolean existsByUsername(String username);
}