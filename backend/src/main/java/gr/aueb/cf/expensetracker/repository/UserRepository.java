package gr.aueb.cf.expensetracker.repository;

import gr.aueb.cf.expensetracker.model.Role;
import gr.aueb.cf.expensetracker.model.User;
import gr.aueb.cf.expensetracker.model.UserGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findUserByEmail(String email);
    List<User> findUsersByUserGroupRole(Role role);
}
