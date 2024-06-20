package gr.aueb.cf.expensetracker.repository;

import gr.aueb.cf.expensetracker.model.Role;
import gr.aueb.cf.expensetracker.model.UserGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserGroupRepository extends JpaRepository<UserGroup, UUID> {
    UserGroup findByRole(Role role);
}
