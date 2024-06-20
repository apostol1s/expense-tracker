package gr.aueb.cf.expensetracker.mapper;

import gr.aueb.cf.expensetracker.DTO.GetAllExpenseDTO;
import gr.aueb.cf.expensetracker.model.Expense;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetAllExpenseDTOMapper {

    /**
     * Maps an Expense entity to a GetAllExpenseDTO.
     *
     * @param expense   the Expense entity to be mapped
     * @return          the mapped GetAllExpenseDTO
     */
    public GetAllExpenseDTO getAllExpenseDTOMapper(Expense expense){
        GetAllExpenseDTO getAllExpenseDto = new GetAllExpenseDTO();
        getAllExpenseDto.setId(expense.getId());
        getAllExpenseDto.setExpenseType(expense.getExpenseType().getCode());
        getAllExpenseDto.setAmount(expense.getAmount());
        getAllExpenseDto.setDescription(expense.getDescription());
        getAllExpenseDto.setCreatedAt(expense.getCreatedAt());
        getAllExpenseDto.setUpdatedAt(expense.getUpdatedAt());
        return getAllExpenseDto;
    }
}
