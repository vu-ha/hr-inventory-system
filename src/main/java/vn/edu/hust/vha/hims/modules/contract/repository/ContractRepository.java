package vn.edu.hust.vha.hims.modules.contract.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.hust.vha.hims.modules.contract.entity.Contract;

@Repository
public interface ContractRepository extends JpaRepository<Contract, UUID> {
    // Thêm custom query nếu cần
}