package gr.aueb.cf.expensetracker.repository;

import gr.aueb.cf.expensetracker.model.AccessPermission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AccessPermissionRepository extends JpaRepository<AccessPermission, UUID> {
}
