package gr.aueb.cf.expensetracker.service;

import gr.aueb.cf.expensetracker.DTO.CreateExpenseDTO;
import gr.aueb.cf.expensetracker.DTO.GetAllExpenseDTO;
import gr.aueb.cf.expensetracker.DTO.UpdateExpenseDTO;

import java.util.List;
import java.util.UUID;

public interface ExpenseService {
    void createExpense(CreateExpenseDTO createExpenseDTO, String email);
    List<GetAllExpenseDTO> getAllExpenses(String email);
    void updateExpense(UpdateExpenseDTO updateExpenseDTO, UUID expenseId, String email);
    void deleteExpense(UUID uuid, String email);
}
