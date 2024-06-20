package gr.aueb.cf.expensetracker.repository;

import gr.aueb.cf.expensetracker.model.Module;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ModuleRepository extends JpaRepository<Module, UUID> {
}
