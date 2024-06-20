package gr.aueb.cf.expensetracker.mapper;

import gr.aueb.cf.expensetracker.DTO.CreateExpenseDTO;
import gr.aueb.cf.expensetracker.model.Expense;
import gr.aueb.cf.expensetracker.model.ExpenseType;
import gr.aueb.cf.expensetracker.service.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class ExpenseMapper {

    /**
     * Maps a CreateExpenseDTO to an Expense entity.
     *
     * @param createExpenseDTO  the DTO containing data for creating an expense
     * @return                  the mapped Expense entity
     */
    public Expense expenseMapper(CreateExpenseDTO createExpenseDTO) {
        Expense expense = new Expense();
        expense.setDescription(createExpenseDTO.getDescription());
        ExpenseType enumType = Arrays.stream(ExpenseType.values())
                .filter(type -> type.getCode().equals(createExpenseDTO.getExpenseType()))
                .findFirst()
                .orElseThrow(() -> new CustomException(HttpStatus.BAD_REQUEST, "Wrong type of expense."));
        expense.setExpenseType(enumType);
        expense.setAmount(createExpenseDTO.getAmount());
        return expense;
    }
}
