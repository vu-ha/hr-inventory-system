package vn.edu.hust.vha.hims.modules.contract.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.hust.vha.hims.modules.contract.entity.ContractType;

@Repository
public interface ContractTypeRepository extends JpaRepository<ContractType, UUID> {
}