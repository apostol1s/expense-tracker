package gr.aueb.cf.expensetracker.service;

import gr.aueb.cf.expensetracker.DTO.CreateExpenseDTO;
import gr.aueb.cf.expensetracker.DTO.GetAllExpenseDTO;
import gr.aueb.cf.expensetracker.DTO.UpdateExpenseDTO;
import gr.aueb.cf.expensetracker.mapper.ExpenseMapper;
import gr.aueb.cf.expensetracker.mapper.GetAllExpenseDTOMapper;
import gr.aueb.cf.expensetracker.model.Expense;
import gr.aueb.cf.expensetracker.model.ExpenseType;
import gr.aueb.cf.expensetracker.model.User;
import gr.aueb.cf.expensetracker.repository.ExpenseRepository;
import gr.aueb.cf.expensetracker.repository.UserRepository;
import gr.aueb.cf.expensetracker.service.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ExpenseServiceImpl implements ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final ExpenseMapper expenseMapper;
    private final UserRepository userRepository;
    private final GetAllExpenseDTOMapper getAllExpenseDTOMapper;

    /**
     * Creates a new expense for the user identified by the given email.
     *
     * @param createExpenseDTO  Data transfer object containing details of the new expense.
     * @param email             Email of the user creating the expense
     */
    @Override
    public void createExpense(CreateExpenseDTO createExpenseDTO, String email) {
        User user = userRepository.findUserByEmail(email).orElseThrow(
                () -> new CustomException(HttpStatus.NOT_FOUND, "User not found"));
        Expense expense = expenseMapper.expenseMapper(createExpenseDTO);
        expense.setUser(user);
        expenseRepository.save(expense);
    }

    /**
     * Retrieves all expenses associated with the user identified by the given email.
     *
     * @param email Email of the user whose expenses are to be retrieved
     * @return      List of GetAllExpenseDTO objects representing all expenses of the user
     */
    @Override
    public List<GetAllExpenseDTO> getAllExpenses(String email) {
        User user = userRepository.findUserByEmail(email).orElseThrow(
                () -> new CustomException(HttpStatus.NOT_FOUND, "User not found"));
        List<Expense> expenses = expenseRepository.findAllByUser(user);

        return expenses.stream()
                .map(getAllExpenseDTOMapper::getAllExpenseDTOMapper)
                .toList();
    }

    /**
     * Updates an existing expense identified by the expenseId for the user identified by email.
     *
     * @param updateExpenseDTO  Data transfer object containing updated expense details.
     * @param expenseId         ID of the expense to be updated.
     * @param email             Email of the user updating the expense.
     */
    @Override
    public void updateExpense(UpdateExpenseDTO updateExpenseDTO, UUID expenseId, String email) {
        User user = userRepository.findUserByEmail(email).orElseThrow(
                () -> new CustomException(HttpStatus.NOT_FOUND, "User not found"));
        Expense expense = expenseRepository.findById(expenseId).orElseThrow(
                () -> new CustomException(HttpStatus.NOT_FOUND, "Expense not found"));
        if (expense.getUser().equals(user)) {
            expense.setDescription(updateExpenseDTO.getDescription());
            ExpenseType enumType = Arrays.stream(ExpenseType.values())
                    .filter(type -> type.getCode().equals(updateExpenseDTO.getExpenseType()))
                    .findFirst()
                    .orElseThrow(() -> new CustomException(HttpStatus.BAD_REQUEST, "Wrong type of expense."));
            expense.setExpenseType(enumType);
            expense.setAmount(updateExpenseDTO.getAmount());
            expenseRepository.save(expense);
            return;
        }
        throw new CustomException(HttpStatus.BAD_REQUEST, "This record has not been created by this user");
    }

    /**
     * Deletes an expense identified by expenseId for the user identified by email.
     *
     * @param expenseId ID of the expense to be deleted.
     * @param email     Email of the user deleting the expense.
     */
    @Override
    public void deleteExpense(UUID expenseId, String email) {
        User user = userRepository.findUserByEmail(email).orElseThrow(
                () -> new CustomException(HttpStatus.NOT_FOUND, "User not found"));
        Expense expense = expenseRepository.findById(expenseId).orElseThrow(
                () -> new CustomException(HttpStatus.NOT_FOUND, "Expense not found"));
        if (expense.getUser().equals(user)) {
            expenseRepository.deleteById(expense.getId());
            return;
        }
        throw new CustomException(HttpStatus.BAD_REQUEST, "This record has not been created by this user");
    }
}
