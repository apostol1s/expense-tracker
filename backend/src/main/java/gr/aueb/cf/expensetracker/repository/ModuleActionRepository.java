package gr.aueb.cf.expensetracker.repository;

import gr.aueb.cf.expensetracker.model.ModuleAction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ModuleActionRepository extends JpaRepository<ModuleAction, UUID> {
}
