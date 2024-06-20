package gr.aueb.cf.expensetracker.repository;

import gr.aueb.cf.expensetracker.model.Expense;
import gr.aueb.cf.expensetracker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ExpenseRepository extends JpaRepository<Expense, UUID> {
    List<Expense> findAllByUser(User user);
}
